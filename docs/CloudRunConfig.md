

gcloud run services describe quotes --region us-central1 --format export >quotes.yaml
gcloud run services replace quotes.yaml

gcloud run services describe bff --region us-central1 --format export >bff.yaml
gcloud run services replace bff.yaml

gcloud run services describe quotes-native --region us-central1 --format export >quotes-native.yaml
gcloud run services replace quotes-native.yaml

gcloud run services describe reference --region us-central1 --format export >reference.yaml
gcloud run services replace reference.yaml

"health check" OR "startup"
HealthCheck OR StartupCheck