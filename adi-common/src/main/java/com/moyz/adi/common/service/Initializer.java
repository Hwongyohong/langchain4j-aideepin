package com.moyz.adi.common.service;

import com.moyz.adi.common.cosntant.AdiConstant;
import com.moyz.adi.common.entity.CompanyModel;
import com.moyz.adi.common.helper.ImageModelContext;
import com.moyz.adi.common.helper.LLMContext;
import com.moyz.adi.common.searchengine.GoogleSearchEngine;
import com.moyz.adi.common.searchengine.SearchEngineContext;
import dev.langchain4j.model.openai.OpenAiModelName;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

@Slf4j
@Service
public class Initializer {

    @Value("${adi.proxy.enable:false}")
    protected boolean proxyEnable;

    @Value("${adi.proxy.host:0}")
    protected String proxyHost;

    @Value("${adi.proxy.http-port:0}")
    protected int proxyHttpPort;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private RAGService ragService;

    @PostConstruct
    public void init() {
        sysConfigService.reload();

        Proxy proxy = null;
        if (proxyEnable) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyHttpPort));
        }

        //openai
        List<CompanyModel> openaiModels = LLMContext.getSupportModels(AdiConstant.CompanyModels.OPENAI_MODEL);
        if (openaiModels!=null) {
            log.warn("openai service is disabled");
        }
        if (openaiModels != null) {
            for (CompanyModel model : openaiModels) {
                LLMContext.addLLMService(model.getName(), model.getLevel(), new OpenAiLLMService(model.getName()).setProxy(proxy));
            }
        }
        //dashscope
        List<CompanyModel> dashscopeModels = LLMContext.getSupportModels(AdiConstant.CompanyModels.DASHSCOPE_MODEL);
        if (dashscopeModels!=null) {
            log.warn("dashscope service is disabled");
        }
        if (dashscopeModels != null) {
            for (CompanyModel model : dashscopeModels) {
                LLMContext.addLLMService(model.getName(),model.getLevel(), new DashScopeLLMService(model.getName()));
            }
        }

        //qianfan
        List<CompanyModel> qianfanModels = LLMContext.getSupportModels(AdiConstant.CompanyModels.QIANFAN_MODEL);
        if (qianfanModels!=null) {
            log.warn("qianfan service is disabled");
        }
        if (qianfanModels != null) {
            for (CompanyModel model : qianfanModels) {
                LLMContext.addLLMService(model.getName(),model.getLevel(), new QianFanLLMService(model.getName()));
            }
        }

        //ollama
        List<CompanyModel> ollamaModels = LLMContext.getSupportModels(AdiConstant.CompanyModels.OLLAMA_MODEL);
        if (ollamaModels!=null ) {
            log.warn("ollama service is disabled");
        }
        if (ollamaModels != null) {
            for (CompanyModel model : ollamaModels) {
                LLMContext.addLLMService("ollama:" + model.getName(),model.getLevel(), new OllamaLLMService(model.getName()));
            }
        }

        ImageModelContext.addImageModelService(OpenAiModelName.DALL_E_2, new OpenAiImageModelService(OpenAiModelName.DALL_E_2).setProxy(proxy));


        //search engine
        SearchEngineContext.addEngine(AdiConstant.SearchEngineName.GOOGLE, new GoogleSearchEngine().setProxy(proxy));


        ragService.init();
    }
}
