Logs filter:
HealthCheck OR StartupCheck


curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-ieuwkt6jkq-uc.a.run.app

QUOTES TEST CR!!!
TOKEN=$(gcloud auth print-identity-token)
time http -A bearer -a $TOKEN https://quotes-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://quotes-native-ieuwkt6jkq-uc.a.run.app/quotes

TOKEN=$(gcloud auth print-identity-token)
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://reference-ieuwkt6jkq-uc.a.run.app/metadata
time http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/metadata
time http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/actuator


---
BFF TEST CR: !!!
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/health
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/startup

time http -A bearer -a $TOKEN https://bff-to-native-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://bff-dont-ieuwkt6jkq-uc.a.run.app/quotes


curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

---
TOKEN=$(gcloud auth print-identity-token)
time http -A bearer -a $TOKEN POST https://bff-ieuwkt6jkq-uc.a.run.app/actuator/health
time http -A bearer -a $TOKEN POST https://bff-ieuwkt6jkq-uc.a.run.app/actuator/startup

time http -A bearer -a $TOKEN POST https://bff-ieuwkt6jkq-uc.a.run.app/quotes author="Henry Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

time http -A bearer -a $TOKEN PUT https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
time http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
time http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6  

gcloud run services get-iam-policy reference --region us-central1
gcloud run services get-iam-policy quotes --region us-central1




---

curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

time http POST :8080/quotes author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

time http PUT :8080/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6  

========

HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.

OkHttp is an HTTP client that’s efficient by default:

HTTP/2 support allows all requests to the same host to share a socket.
Connection pooling reduces request latency (if HTTP/2 isn’t available).
Transparent GZIP shrinks download sizes.
Response caching avoids the network completely for repeat requests.
OkHttp perseveres when the network is troublesome: it will silently recover from common connection problems. If your service has multiple IP addresses, OkHttp will attempt alternate addresses if the first connect fails. This is necessary for IPv4+IPv6 and services hosted in redundant data centers. OkHttp supports modern TLS features (TLS 1.3, ALPN, certificate pinning). It can be configured to fall back for broad connectivity.

Using OkHttp is easy. Its request/response API is designed with fluent builders and immutability. It supports both synchronous blocking calls and async calls with callbacks.

---
makeAuthenticatedRequest creates authenticated requests to private services. It uses the Google Cloud metadata server in the Cloud Run environment to create an identity token and add it to the HTTP request as part of an Authorization header.

In other environments, makeAuthenticatedRequest requests an identity token from Google's servers by authenticating with Application Default Credentials.

The backend service is private using Cloud Run's built-in, IAM-based service-to-service authentication feature, that limits who can call the service. Both services are built with the principle of least privilege, with no access to the rest of Google Cloud except where necessary.