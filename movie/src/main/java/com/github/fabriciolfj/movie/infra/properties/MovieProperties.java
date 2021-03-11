package com.github.fabriciolfj.movie.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "movie")
public class MovieProperties {
    private String directory;
    private String filePattern;
    private Boolean preventDuplicates;
    private Long fixedDelay;
    private String remoteService = "http://localhost:8181/v1/movie";

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public Boolean getPreventDuplicates() {
        return preventDuplicates;
    }

    public void setPreventDuplicates(Boolean preventDuplicates) {
        this.preventDuplicates = preventDuplicates;
    }

    public Long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public String getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(String remoteService) {
        this.remoteService = remoteService;
    }
}
