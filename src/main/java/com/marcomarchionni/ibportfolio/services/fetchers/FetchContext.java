package com.marcomarchionni.ibportfolio.services.fetchers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class FetchContext {
    MultipartFile file;
}
