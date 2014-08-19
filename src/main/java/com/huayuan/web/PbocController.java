package com.huayuan.web;

import com.huayuan.common.App;
import com.huayuan.common.CommonDef;
import com.huayuan.common.integration.ImageTools;
import com.huayuan.common.util.OperUtil;
import com.huayuan.common.util.OtsuBinarize;
import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;
import com.huayuan.repository.credit.PbocRepository;
import com.huayuan.repository.member.IdCardRepository;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.CropZoomData;
import com.huayuan.web.dto.ImageCropDto;
import com.huayuan.web.dto.PbocOutDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
@Controller
@RequestMapping(value = "/pboc")
public class PbocController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
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
        List<PbocOutDto> pbocOutDtoList = pbocRepository.searchPbocOutList("");
        return pbocOutDtoList;
    }

    /**
     * 复制身份照片
     *
     * @param idCard
     * @param flag   0:front and back 1:front  2:back
     */
    private void copyIdCard(IdCard idCard, String flag) {
        final String path = App.getInstance().getIdCardImageBase();
        File destDir = new File(path + CommonDef.IDCARD_PROCESS_DIR);
        if (!destDir.exists() || !destDir.isDirectory()) {
            destDir.mkdirs();
        }
        if ("0".equals(flag) || "1".equals(flag)) {
            try {
                OperUtil.copyFile(path + "/" + idCard.getImageFront(), path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageFront());
            } catch (IOException e) {
                log.error("OperUtil copyFile " + idCard.getImageFront() + " :", e);
            }
        }
        if ("0".equals(flag) || "2".equals(flag)) {
            try {
                OperUtil.copyFile(path + "/" + idCard.getImageBack(), path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageBack());
            } catch (IOException e) {
                log.error("OperUtil copyFile " + idCard.getImageBack() + " :", e);
            }
        }
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
    public List<PbocSummary> search(@RequestParam("curPage") Integer curPage, @RequestParam("q") String query) {
        return pbocRepository.search(curPage, query);
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
        final String path = App.getInstance().getIdCardImageBase();
        List<IdCard> idCards = idCardRepository.findFromPbocOut();
        for (IdCard idCard : idCards) {
            final String front = path + "/" + idCard.getImageFront();
            final String back = path + "/" + idCard.getImageBack();
            exportToPdf(path + CommonDef.IDCARD_TEMP + "/" + idCard.getIdNo() + ".pdf", new String[]{front, back});
        }
    }

    private void exportToPdf(String pdfFileName, String[] images) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
            document.open();
            for (int i = 0; i < images.length; i++) {
                String imageFileName = images[i];
                try {
                    Image image = Image.getInstance(OtsuBinarize.binarize(imageFileName));
                    image.scaleAbsolute(243f, 153f);
                    if (i == 1) {
                        image.setAbsolutePosition(100f, 400f);
                    }
                    document.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            document.close();
        } catch (Exception e) {
            log.error("exportToPdf", e);
        }
    }

    @RequestMapping(value = "/export/{idNo}", method = RequestMethod.GET)
    @ResponseBody
    public String exportIdCardToPdf(@PathVariable String idNo) {
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if (idCards == null || idCards.isEmpty()) {
            return "0";
        }
        generatePdfByIdCard(idCards.get(0));
        return "1";
    }

    @RequestMapping(value = "/export/list/{idNos}", method = RequestMethod.GET)
    @ResponseBody
    public String exportIdCardToZip(@PathVariable String idNos) {
        String query = "";
        if (StringUtils.isNotEmpty(idNos)) {
            query = idNos.substring(0, idNos.length() - 1);
        } else {
            return "";
        }
        query = "'" + query.replaceAll(",", "','") + "'";

        List<IdCard> idCards = idCardRepository.findByIdNos(query);
        if (idCards == null || idCards.isEmpty()) {
            return "";
        }
        for (IdCard idCard : idCards) {
            generatePdfByIdCard(idCard);
        }
        return packagePdfToZip(idNos);
    }

    private void generatePdfByIdCard(IdCard idCard) {
        final String path = App.getInstance().getIdCardImageBase();
        final String front = path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageFront();
        final String back = path + CommonDef.IDCARD_PROCESS_DIR + "/" + idCard.getImageBack();
        String pdfName = idCard.getIdNo() + ".pdf";
        exportToPdf(path + CommonDef.IDCARD_TEMP + "/" + pdfName, new String[]{front, back});
    }

    private String packagePdfToZip(String idNos) {
        final String path = App.getInstance().getIdCardImageBase();
        String[] pdfNames = idNos.split(",");
        File[] files = new File[pdfNames.length];
        for (int i = 0; i < pdfNames.length; i++) {
            files[i] = new File(path + CommonDef.IDCARD_TEMP + "/" + pdfNames[i] + ".pdf");
        }
        String name = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".zip";
        OperUtil.packageToZip(files, path + CommonDef.IDCARD_TEMP, name);
        return name;
    }

    @RequestMapping(value = "/idcard/{idNo}/{frontFlag}", method = RequestMethod.POST)
    @ResponseBody
    public String reuseOriginPic(@PathVariable String idNo, @PathVariable("frontFlag") String flag) {
        if (StringUtils.isEmpty(idNo)) {
            return "0";
        }
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if (CollectionUtils.isEmpty(idCards)) {
            return "0";
        }
        IdCard idCard = idCards.get(0);
        if ("1".equals(flag)) {
            copyIdCard(idCard, "1");
        } else if ("2".equals(flag)) {
            copyIdCard(idCard, "2");
        }
        return "1";
    }

    @RequestMapping(value = "/process/all", method = RequestMethod.GET)
    @ResponseBody
    public String processIdcardAll() {
        List<PbocOutDto> pbocOutDtoList = pbocRepository.searchPbocOutList("");
        if (CollectionUtils.isNotEmpty(pbocOutDtoList)) {
            StringBuffer idNoSb = new StringBuffer(128);
            for (PbocOutDto dto : pbocOutDtoList) {
                idNoSb.append(dto.getIdNo() + ",");
            }
            String idNos = idNoSb.toString();
            String query = idNos.substring(0, idNos.length() - 1);
            query = "'" + query.replaceAll(",", "','") + "'";

            List<IdCard> idCards = idCardRepository.findByIdNos(query);
            for (IdCard idCard : idCards) {
                copyIdCard(idCard, "0");
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
    public String cropIdCardImage(@PathVariable String idNo, @RequestBody ImageCropDto imageCropDto) {
        final String path = App.getInstance().getIdCardImageBase() + CommonDef.IDCARD_PROCESS_DIR;
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if (idCards == null || idCards.isEmpty()) {
            return "0";
        }
        IdCard idCard = idCards.get(0);
        String imageName = "";
        if ("1".equals(imageCropDto.getType())) { // Front IdCard Image
            imageName = idCard.getImageFront();
        } else if ("2".equals(imageCropDto.getType())) { // Back IdCard Image
            imageName = idCard.getImageBack();
        }
        cropImageWrap(path, imageName, imageCropDto);
        return "1";
    }

    @RequestMapping(value = "/rotate/{idNo}", method = RequestMethod.POST)
    @ResponseBody
    public String ratoteIdCardImage(@PathVariable String idNo, @RequestBody CropZoomData cropZoomData) {
        List<IdCard> idCards = idCardRepository.findByIdNo(idNo);
        if (idCards == null || idCards.isEmpty()) {
            return "0";
        }
        IdCard idCard = idCards.get(0);
        final String path = App.getInstance().getIdCardImageBase() + CommonDef.IDCARD_PROCESS_DIR;
        String imagePath = "";
        if (cropZoomData.getImageSource().contains(idCard.getImageFront())) {
            imagePath = path + "/" + idCard.getImageFront();
        } else if (cropZoomData.getImageSource().contains(idCard.getImageBack())) {
            imagePath = path + "/" + idCard.getImageBack();
        }
        try {
            if (cropZoomData.getImageRotate() != 0) {
                ImageTools.rotateImage(imagePath, imagePath, cropZoomData.getImageRotate());
            }
        } catch (Exception e) {
            log.error("ratoteIdCardImage", e);
        }
        return "1";
    }

    private void cropImageWrap(String imagePath, String imageName, ImageCropDto imageCropDto) {
        try {
            String srcPath = imagePath + "/" + imageName;
            BufferedImage original = ImageIO.read(new File(srcPath));
            double scaleX = ((double) original.getWidth()) / imageCropDto.getFixedWidth();
            double scaleY = ((double) original.getHeight()) / imageCropDto.getFixedHeight();
            int x = (int) (imageCropDto.getX() * scaleX);
            int y = (int) (imageCropDto.getY() * scaleY);
            int width = (int) (imageCropDto.getWidth() * scaleX);
            int height = (int) (imageCropDto.getHeight() * scaleY);
            OperUtil.cropImage(srcPath, srcPath, x, y, width, height);
            ImageTools.resizeImage(srcPath, srcPath, 256, 162);
        } catch (Exception e) {
            log.error("OperUtil cropImageWrap " + imageName + " :", e);
        }
    }
}
