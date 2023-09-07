#include <string.h>
#include <stdio.h>
#include <ctype.h>

void remvSpace(char *s)
{
    char *d = s;
    do
    {
        while (*d == ' ')
        {
            ++d;
        }
    } while (*s++ = *d++);
}

void rev(char s[], int i, int len)
{
    if (i >= len / 2)
        return;

    char temp = s[i];
    s[i] = s[len - i - 1];
    s[len - i - 1] = temp;

    rev(s, i + 1, len);
}

int main()
{
    char str[100];
    char str2[100];
    int igual;

    while (1)
    {
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0';

        if (strcmp(str, "FIM") == 0)
            break;

        strcpy(str2, str);

        remvSpace(str);
        remvSpace(str2);

        rev(str, 0, strlen(str));

        igual = strncmp(str, str2, strlen(str));

        if (igual == 0)
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
    }

    return 0;
}