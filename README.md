Write values in .env
    SMTP_HOST - i used smtp.gmail.com
    SMTP_PORT - port, used standart 587
    SMTP_EMAIL - your email
    SMTP_PASS - smtp api pass

Start docker-compose.yml

How to send:
    http://localhost:8080/emails/send
    Example json:
    STATUS can be SENDING, SENT, ERROR
    If you want to send, write SENDING status
    If after 5 minute use ElasticSearch post (http://localhost:9200/letters/_doc)
    {
        "subject": "SOME",
        "content": "SOME,
        "email": "ToMail",
        "status": "SENDING",
        "timestamp": "2023-02-19T17:00:00"
    } # email-sender-example
# email-sender-example
# email-sender-example
