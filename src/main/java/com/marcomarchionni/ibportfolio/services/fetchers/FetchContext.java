package com.marcomarchionni.ibportfolio.services.fetchers;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Setter
@Getter
public class FetchContext {
    InputStream stream;
}
