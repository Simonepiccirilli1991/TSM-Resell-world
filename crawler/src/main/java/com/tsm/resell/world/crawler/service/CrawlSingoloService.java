package com.tsm.resell.world.crawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlSingoloService {


    // fa uno scrapping del singolo nome passato in reuqest verso ebay, e torna la lista di cio che trova
    public Object crawlSingol(String nome, HttpHeaders headers){

        //TODO; creare logica che passando un nome fa ricerca su venduti ebay, prendendo nome data prezzo e foto, dopo di che li torna e chiama llm per farli filtrare
    }
}
