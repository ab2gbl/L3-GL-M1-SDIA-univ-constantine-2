#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define KEYSIZE 16

int main() {
    int i;
    long long start_time, end_time;
    unsigned char key[KEYSIZE];

    struct tm timeinfo = {0};
    timeinfo.tm_year = 2018 - 1900; // Year since 1900
    timeinfo.tm_mon = 3;            // Month (0-11)
    timeinfo.tm_mday = 18;          // Day of the month
    timeinfo.tm_hour = 22;          // Hour
    timeinfo.tm_min = 1;            // Minute
    timeinfo.tm_sec = 49;           // Second
    end_time = mktime(&timeinfo);
    printf("Unix Timestamp: %lld\n", (long long)end_time);

    start_time = end_time - 24 * 3600;

    FILE *fp = fopen("keys.txt", "w");
    if (fp == NULL) {
        printf("Failed to open file for writing.\n");
        return 1;
    }

    for (long long t = start_time; t <= end_time; t++) {
        srand(t);
        for (i = 0; i < KEYSIZE; i++) {
            key[i] = rand() % 256;  
        }

        fprintf(fp, "%lld,", t);  
        for (i = 0; i < KEYSIZE; i++) {
            fprintf(fp, "%.2x", (unsigned char) key[i]);
        }
        fprintf(fp, "\n");
    }

    fclose(fp);
    printf("Done generating keys.\n");

    return 0;
}
