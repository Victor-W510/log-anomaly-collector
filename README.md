## System Context

This service is part of a distributed log anomaly detection system.

**Overall architecture:**

| Service | Role |
|---|---|
| Platform | Docker orchestration + system entry point |
| Collector | Log ingestion and persistence |
| ML Service | Anomaly detection and classification |

> For full system setup and orchestration, see the [platform repository](https://github.com/Victor-W510/log-anomaly-platform).
