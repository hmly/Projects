#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void error(const char *msg)
{
    char error_msg[100];
    strcpy(error_msg, "ERROR ");
    strncat(error_msg, msg, 94);
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;
    char buf[256];

    if (argc < 3) {
        fprintf(stderr, "Usage: %s <hostname> <port>\n", argv[0]);
        exit(1);
    }

    /* Create socket */
    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
        error("opening socket");
    portno = atoi(argv[2]);
    server = gethostbyname(argv[1]);
    if (server == NULL) error("no such host");

    /* Connect socket */
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *) server->h_addr, (char *) &serv_addr.sin_addr.s_addr, 
        server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
        error("connecting");

    /* Get message */
    while (1) {
        printf("Enter the message: ");
        bzero(buf, 256);
        fgets(buf, 256, stdin);
        n = write(sockfd, buf, strlen(buf));

        if (n < 0) error("writing to socket");
        bzero(buf, 256);
        n = read(sockfd, buf, 256);
        if (n < 0) error("reading from socket");
        printf("%s\n", buf);
    }

    close(sockfd);
    exit(0);
}
