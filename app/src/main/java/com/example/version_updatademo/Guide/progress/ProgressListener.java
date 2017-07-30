package com.example.version_updatademo.Guide.progress;

public interface ProgressListener {

    void progress(long bytesRead, long contentLength, boolean done);

}
