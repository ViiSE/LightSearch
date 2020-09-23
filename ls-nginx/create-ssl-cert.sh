DIR="keys/"

function job {
    mkdir keys
    chmod ugo+rw keys/

    echo "+-------------------------+"
    echo "|OpenSSL|RSA 4096|365 days|"
    echo "+-------------------------+"

    openssl req -x509 -nodes -days 365 -newkey rsa:4096 -keyout keys/certificate.key -out keys/certificate.crt

    echo "Key and certificate is created!"

    echo "Create Diffie-Hellman group..."
    openssl dhparam -out keys/dhparam.pem 4096
    echo "Done!"

    echo "Create read/write privilige..."
    chmod ugo+rw keys/dhparam.pem
    chmod ugo+rw keys/certificate.crt
    chmod ugo+rw keys/certificate.key

    echo "Key and certificate saved in keys/ folder"
    echo "Don't forget: key and certificate are valid for 365 days."
}

echo "Create self-signed key and certificate..."
if [ -d "$DIR" ]; then
  read -p "Folder $DIR is exist. Erase all data in folder and continue?" -n 1 -r
  echo    # (optional) move to a new line
  if [[ $REPLY =~ ^[Yy]$ ]]
  then
    rm -rf keys/
    job
  else
    exit 0
  fi
else
  job
fi