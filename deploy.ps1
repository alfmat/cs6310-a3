docker compose build --no-cache
docker compose up -d 
write-host "Running Docker Build For Delivery Service..."
docker build -t cs6310-a3/service ./service --no-cache
Write-Host "Build was successful==>"
docker run --rm --network=cs6310-a3_default -it cs6310-a3/service
docker compose down