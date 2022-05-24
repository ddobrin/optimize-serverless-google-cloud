

gcloud compute addresses create google-managed-quotes \
--global --purpose=VPC_PEERING --prefix-length=16 \
--description="peering range for Google" --network=default

gcloud services vpc-peerings connect --service=servicenetworking.googleapis.com \
--ranges=google-managed-quotes --network=default \
--project=dan-workshop-project-332213
----------

DB_ROOT_PASSWORD=quote-root-pwd
DB_INSTANCE=quote-cr-db-instance
DB_NAME=quote_db
DB_USER=quote-user

gcloud beta sql instances create $DB_INSTANCE \
--database-version=POSTGRES_14 \
 --cpu=2 \
 --memory=4GB \
 --region=us-central \
 --root-password=$DB_ROOT_PASSWORD \
 --no-assign-ip \
--network=default

Creating Cloud SQL instance...done.                                                                                                               
Created [https://sqladmin.googleapis.com/sql/v1beta4/projects/dan-workshop-project-332213/instances/quote-cr-db-instance].
NAME                  DATABASE_VERSION  LOCATION       TIER              PRIMARY_ADDRESS  PRIVATE_ADDRESS  STATUS
quote-cr-db-instance  POSTGRES_14       us-central1-a  db-custom-2-4096  -                172.24.0.10      RUNNABLE
---------

DB_NAME=quote_db
gcloud sql databases create $DB_NAME --instance=$DB_INSTANCE

---------

gcloud sql users create quote_user \
--instance=quote_db \
--password=quote-cr-db-pwd

----------

gcloud iam service-accounts create cr-quote-service-account \
  --display-name="Quote service Cloud Run Service Account"

gcloud projects add-iam-policy-binding dan-workshop-project-332213 \
  --member="serviceAccount:cr-quote-service-account@dan-workshop-project-332213.iam.gserviceaccount.com" \
  --role="roles/cloudsql.client" 

---------

Create Serverless VPC connector
"quote-connector"

Subrange - custom: 10.8.0.0

----------

