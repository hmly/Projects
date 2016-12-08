#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
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
    int sockfd, newsockfd, portno;
    struct sockaddr_in serv_addr, cli_addr;
    socklen_t clilen;
    char buf[128];
    int n;

    if (argc < 2) {
        fprintf(stderr, "Usage: %s <port no>\n", argv[0]);
        exit(1);
    }

    /* Create socket */
    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
        error("opening socket");
    bzero((char *) & serv_addr, sizeof(serv_addr));
    portno = atoi(argv[1]);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    /* Bind socket */
    if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
        error("on binding");
    listen(sockfd, 5);

    /* Receive client requests */
    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
    printf("waiting for requests...\n");
    while (1) {
        if (newsockfd < 0) error("on accept");
        bzero(buf, 128);
        n = read(newsockfd, buf, 128);

        /* Get message */
        if (n < 0) error("reading from socket");
        printf("> %s\n", buf);
        n = write(newsockfd, "text file", 12);
        if (n < 0) error("writing to socket");
    }

    close(sockfd);
    close(newsockfd);
    exit(0);
}
