package com.marcomarchionni.ibportfolio.services.fetchers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class FetchContext {
    SourceType sourceType;
    MultipartFile file;

    public enum SourceType {
        FILE, SERVER
    }
}
