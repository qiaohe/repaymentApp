package com.huayuan.web;

import com.huayuan.common.App;
import com.huayuan.common.CommonDef;
import com.huayuan.common.util.OperUtil;
import com.huayuan.common.util.OtsuBinarize;
import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;
import com.huayuan.repository.credit.PbocRepository;
import com.huayuan.repository.integration.MatlabIdCardPreProcessor;
import com.huayuan.repository.member.IdCardRepository;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.ImageCropDto;
import com.huayuan.web.dto.PbocOutDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by dell on 14-4-28.
 */
@Controller
@RequestMapping(value = "/pboc")
public class PbocController {
    @Inject
    private PbocRepository pbocRepository;
    @Inject
    private IdCardRepository idCardRepository;

    @Inject
    private MemberService memberService;

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocSummary> getSummary() {
        return pbocRepository.findByStatus();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "pbocSummary";
    }

    @RequestMapping(value = "/{id}/idCard", method = RequestMethod.GET)
    @ResponseBody
    public String getIdCardImage(@PathVariable Long id) {
        return pbocRepository.getIdCardImage(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Pboc getPbocBy(@PathVariable Long id) {
        return pbocRepository.findOne(id);
    }

    @RequestMapping(value = "/out/list", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocOutDto> searchPbocOutList() {
        return pbocRepository.searchPbocOutList("");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Pboc updatePbocBy(@RequestBody Pboc pboc) {
        if (pboc.getStatus().equals(Pboc.CHANGE_ID)) {
            IdCard idCard = idCardRepository.findByIdNo(pboc.getCertNo()).get(0);
            idCard.setIdNo(pboc.getNewCertNo());
            idCard.setName(pboc.getNewName());
            pboc.setName(pboc.getNewName());
            pboc.setCertNo(pboc.getNewCertNo());
            idCardRepository.save(idCard);
        }
        return pbocRepository.save(pboc);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocSummary> search(@RequestParam("curPage") Integer curPage,@RequestParam("q") String query) {
        return pbocRepository.search(curPage,query);
    }

    @RequestMapping(value = "/{idCardNo}/phone/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String getMobilePhone(@PathVariable String idCardNo, @PathVariable Integer type) {
        if (type == 5) return memberService.getPhone(idCardNo);
        Pboc pboc = pbocRepository.findByCertNo(idCardNo);
        if (type == 1) return pboc.getMobile();
        if (type == 2) return pboc.getOfficeTelephoneNo();
        if (type == 3) return pboc.getHomeTelephoneNo();
        if (type == 4) return pboc.getPartnerTelephoneNo();
        return null;
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void exportIdCardAsPdf() {
        final String path = App.getInstance().getIdCardImageBase() + "/";
        List<IdCard> idCards = idCardRepository.findFromPbocOut();
        for (IdCard idCard : idCards) {
            final String front = path + idCard.getImageFront();
            final String back = path + idCard.getImageBack();
            exportToPdf(path + idCard.getIdNo() + ".pdf", new String[]{front, back});
        }
    }

    private void exportToPdf(String pdfFileName, String[] images) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
            document.open();
            for (String imageFileName : images) {
                try {
                    Image image = Image.getInstance(OtsuBinarize.binarize(imageFileName));
                    image.scaleAbsolute(243f, 153f);
                    document.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/export/{idNo}", method = RequestMethod.GET)
    @ResponseBody
    public String exportIdCardToPdf(@PathVariable String idNo) {
        final String path = App.getInstance().getIdCardImageBase();
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if(idCards == null || idCards.isEmpty()) {
            return "0";
        }
        IdCard idCard = idCards.get(0);
        String pdfName = "";
        final String front = path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageFront();
        final String back = path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageBack();
        pdfName = idCard.getIdNo() + ".pdf";
        exportToPdf(path + "/" + pdfName, new String[]{front, back});
        return "1";
    }

    @RequestMapping(value = "/export/list/{idNos}", method = RequestMethod.GET)
    @ResponseBody
    public String exportIdCardToZip(@PathVariable String idNos) {
        String query = "";
        if(StringUtils.isNotEmpty(idNos)) {
            query = idNos.substring(0,idNos.length()-1);
        } else {
            return "";
        }
        query = "'"+query.replaceAll(",","','")+"'";

        List<IdCard> idCards = idCardRepository.findByIdNos(query);
        if(idCards == null || idCards.isEmpty()) {
            return "";
        }
        final String path = App.getInstance().getIdCardImageBase();
        for(IdCard idCard : idCards) {
            String pdfName = "";
            final String front = path +CommonDef.IDCARD_PROCESS_DIR +"/"+ idCard.getImageFront();
            final String back = path +CommonDef.IDCARD_PROCESS_DIR +"/"+ idCard.getImageBack();
            pdfName = idCard.getIdNo() + ".pdf";
            exportToPdf(path + "/" + pdfName, new String[]{front, back});
        }
        return packagePdfToZip(path, idNos);
    }

    private String packagePdfToZip(String path,String idNos) {
        String[] pdfNames = idNos.split(",");
        File[] files = new File[pdfNames.length];
        for(int i = 0; i < pdfNames.length; i++) {
            files[i] = new File(path+pdfNames[i]+".pdf");
        }
        String name = UUID.randomUUID().toString() +".zip";
        OperUtil.packageToZip(files,path+CommonDef.IDCARD_TEMP,name);
        return name;
    }

    @RequestMapping(value = "/process/all", method = RequestMethod.GET)
    @ResponseBody
    public String processIdcardAll() {
        final String path = App.getInstance().getIdCardImageBase();
        final String destPath = path + CommonDef.IDCARD_PROCESS_DIR;
        File destDir = new File(destPath);
        if(!destDir.exists() || !destDir.isDirectory()) {
            destDir.mkdirs();
        }
        MatlabIdCardPreProcessor processor = new MatlabIdCardPreProcessor();
        boolean result = processor.batchPreProcessingImage(path,destPath);
        return result ? "1" : "0";
    }

    @RequestMapping(value = "/process/batch/{idNos}", method = RequestMethod.GET)
    @ResponseBody
    public String processIdcardBatch(@PathVariable String idNos) {
        final String path = App.getInstance().getIdCardImageBase();
        String[] idNoArr = idNos.split(",");
        MatlabIdCardPreProcessor processor = new MatlabIdCardPreProcessor();
        for(String idNo : idNoArr) {
            List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
            if(idCards == null || idCards.isEmpty()) {
                continue;
            }
            IdCard idCard = idCards.get(0);
            if(idCard != null) {
                processor.preProcessingImage(path +"/"+ idCard.getImageFront(),path+CommonDef.IDCARD_PROCESS_DIR);
                processor.preProcessingImage(path +"/"+ idCard.getImageBack(),path+CommonDef.IDCARD_PROCESS_DIR);
            }
        }
        return "1";
    }

    @Scheduled(cron = "0 0 * * * ?")
    public static void removePdfZipTemp() {
        String path = App.getInstance().getIdCardImageBase() + CommonDef.IDCARD_TEMP;
        OperUtil.deleteDirectory(path);
    }

    @RequestMapping(value = "/crop/{idNo}", method = RequestMethod.POST)
    @ResponseBody
    public String cropIdCardImage(@PathVariable String idNo,@RequestBody ImageCropDto imageCropDto) {
        final String path = App.getInstance().getIdCardImageBase() + CommonDef.IDCARD_PROCESS_DIR;
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if(idCards == null || idCards.isEmpty()) {
            return "0";
        }
        IdCard idCard = idCards.get(0);
        String imageName = "";
        if("1".equals(imageCropDto.getType())) { // Front IdCard Image
            imageName = idCard.getImageFront();
        } else if("2".equals(imageCropDto.getType())) { // Back IdCard Image
            imageName = idCard.getImageBack();
        }
        cropImageWrap(path,imageName,imageCropDto);
        return "1";
    }

    private void cropImageWrap(String imagePath,String imageName,ImageCropDto imageCropDto) {
        try {
            String srcPath = imagePath+imageName;
            BufferedImage original = ImageIO.read(new File(srcPath));
            double scaleX = ((double)original.getWidth())/imageCropDto.getFixedWidth();
            double scaleY = ((double)original.getHeight())/imageCropDto.getFixedHeight();
            int x = (int) (imageCropDto.getX() * scaleX);
            int y = (int) (imageCropDto.getY() * scaleY);
            int width = (int) (imageCropDto.getWidth() * scaleX);
            int height = (int) (imageCropDto.getHeight() * scaleY);
            String destPath = imagePath + CommonDef.IDCARD_PROCESS_DIR;
            File destDir = new File(destPath);
            if(!destDir.exists() || !destDir.isDirectory()) {
                destDir.mkdirs();
            }
            OperUtil.cropImage(srcPath, destPath+"/"+imageName, x, y, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
