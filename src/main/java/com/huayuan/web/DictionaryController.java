package com.huayuan.web;

import com.huayuan.domain.dictionary.Dictionary;
import com.huayuan.domain.dictionary.ValueBin;
import com.huayuan.repository.DictionaryRepository;
import com.huayuan.repository.ValueBinRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-4-4.
 */
@Controller
@RequestMapping("/dict")
public class DictionaryController {
    @Inject
    private ValueBinRepository valueBinRepository;
    @Inject
    private DictionaryRepository dictionaryRepository;

    @RequestMapping(value = "/binCode", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ValueBin> getBankBinCode() {
        return valueBinRepository.findAll();
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Dictionary> getDictionaryBy(@PathVariable String type) {
        return dictionaryRepository.findByType( StringUtils.upperCase(type));
    }
}
