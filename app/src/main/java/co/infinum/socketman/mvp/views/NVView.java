package co.infinum.socketman.mvp.views;

public interface NVView extends SocketView {

    void showEndpointDialog(String endpoint);

    void appendSocketLog(String log);

    void cleanSocketLog();
}
