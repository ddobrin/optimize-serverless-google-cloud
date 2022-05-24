

gcloud run services describe quotes --region us-central1 --format export >quotes.yaml

gcloud run services replace quotes.yaml