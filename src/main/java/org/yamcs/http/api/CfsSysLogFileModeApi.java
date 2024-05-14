package org.yamcs.http.api;

import com.windhoverlabs.yamcs.cfs.sys_log.api.AbstractCfsSysLogFileModeApi;
import com.windhoverlabs.yamcs.cfs.sys_log.api.CfsSysLogFileModeConfig;
import com.windhoverlabs.yamcs.cfs.sys_log.api.SetCFSSysLogFileModeRequest;
import org.yamcs.YamcsServer;
import org.yamcs.api.Observer;
import org.yamcs.events.EventProducer;
import org.yamcs.events.EventProducerFactory;
import org.yamcs.http.Context;
import org.yamcs.tctm.Link;

public class CfsSysLogFileModeApi extends AbstractCfsSysLogFileModeApi<Context> {

  private EventProducer eventProducer =
      EventProducerFactory.getEventProducer(null, this.getClass().getSimpleName(), 10000);

  @Override
  public void setMode(
      Context c, SetCFSSysLogFileModeRequest request, Observer<CfsSysLogFileModeConfig> observer) {
    Link l =
        YamcsServer.getServer()
            .getInstance(request.getInstance())
            .getLinkManager()
            .getLink(request.getLinkName());

    if (l == null) {
      eventProducer.sendInfo("Link:" + request.getLinkName() + " does not exist");
      observer.complete();
      return;
    }
    ((com.windhoverlabs.yamcs.cfs.sys_log.CfsSysLogPlugin) l).setMode(request.getMode());
    observer.complete(CfsSysLogFileModeConfig.newBuilder().build());
  }
}
