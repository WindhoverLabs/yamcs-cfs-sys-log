[![CI](https://github.com/WindhoverLabs/yamcs-cfs-ds/actions/workflows/ci.yml/badge.svg)](https://github.com/WindhoverLabs/yamcs-cfs-ds/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/WindhoverLabs/yamcs-cfs-ds/badge.svg?branch=main)](https://coveralls.io/github/WindhoverLabs/yamcs-cfs-ds?branch=main)
# yamcs-cfs-sys-log
A YAMCS plugin for the Core Flight System (CFS) Sys Log format. It has the ability to parse
sys logs by removing the `CFE_FS_Header_t`  and dumping the contents to a text file. 

# Table of Contents
1. [Dependencies](#dependencies)
2. [To Build](#to_build)  

### Dependencies <a name="dependencies"></a>
- `Java 11`
- `Maven`
- `YAMCS>=5.6.2`
- `Ubuntu 16/18/20`

### To Build <a name="to_build"></a>
```
mvn install -DskipTests
```

To package it:
```
mvn package -DskipTests
mvn dependency:copy-dependencies
```

The `package` command will output a jar file at `yamcs-cfs-ds/target`.
Note the `dependency:copy-dependencies` command; this will copy all of the jars to the `yamcs-cfs-ds/target/dependency` directory. Very useful for integrating third-party dependencies.

### To Integrate with YAMCS
This plugin functions as a YAMCS Telemetry Provider and will appear as a Datalink.  To integrate this plugin, add the
"com.windhoverlabs.yamcs.cfs.sys_log.CfsSysLogPlugin" plugin to the "dataLinks" section of the YAMCS instance configuration. 
For example:
```yaml
dataLinks:
  - name: sys-logs
    class: com.windhoverlabs.yamcs.cfs.sys_log.CfsSysLogPlugin
    stream: tm_realtime
    buckets: ["cfdpDown"]
    CFE_FS_ES_SYSLOG_SUBTYPE: 2
    #CFE_ES_SYSTEM_LOG_SIZE=value of CFE_ES_SYSTEM_LOG_SIZE macro in CFS
    CFE_ES_SYSTEM_LOG_SIZE: 3072  # Ignored when readUntilEOF is true
    readUntilEOF: false
    sysLogFileConfig:
      mode: APPEND # APPEND, REPLACE, INACTIVE
      outputFile: cfe_es_sys_log.txt
      sysLogBucket: "cfdpDown"
```


### EVS CSV Mode API

```python
import requests
r = requests.post('http://127.0.0.1:8090/api/fsw/err_log/csv/mode/',
                  json={"instance": "fsw",
                        "linkName": "sys-logs",
                        "mode": "INACTIVE"})
```
```python
import requests
r = requests.post('http://127.0.0.1:8090/api/fsw/err_log/csv/mode/',
                  json={"instance": "fsw",
                        "linkName": "sys-logs",
                        "mode": "APPEND"})
```
```python
import requests
r = requests.post('http://127.0.0.1:8090/api/fsw/err_log/csv/mode/',
                  json={"instance": "fsw",
                        "linkName": "sys-logs",
                        "mode": "REPLACE"})
```
