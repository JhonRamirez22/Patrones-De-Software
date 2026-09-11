package com.globaldocs.processor;

import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentType;

public interface DocumentProcessor {

    String process(String fileName, String content);

    DocumentType getType();

    DocumentFormat getFormat();
}
