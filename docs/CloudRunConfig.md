

gcloud run services describe quotes --region us-central1 --format export >quotes.yaml
gcloud run services replace quotes.yaml

gcloud run services describe bff --region us-central1 --format export >bff.yaml
gcloud run services replace bff.yaml