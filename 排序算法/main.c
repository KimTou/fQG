#include"qgsort.h"
#include <stdio.h>
#include <stdlib.h>
#include<time.h>

int main()
{
    //Add();                  //�����������ɳ���
    Read();                   //��ȡ�������ݲ������������ĳ���
/*
    int a[10000]={0};
    Test(a,10000);
    int b[50000]={0};
    Test(b,50000);             //�������������С�����������Գ���
    int static c[200000] ;
    Test(c,200000);
    Test2();
*/
    return 0;
}

void Add()
{
    int i,j,c,z,p;       //��ֹ���벻������
    char a[20];
    printf("�������������ɶ�����������txt�ļ�(1~1000000)��\n");
    while(1){
        scanf("%s",a);
        for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
        {
            if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
                j++;
        }
        if(j==strlen(a)){                           //��ֹ��������������
            p=0;
            for(c=1;j>0;j--){
                z=(int)a[j-1]-48;
                p+=z*c;
                c*=10;
            }
        if(p>0&&p<1000000)
            break;
        else
            printf("����ȷ����������\n�������룺");
        }
        else
            printf("����ȷ����������\n�������룺");
    }
    FILE *fp;
    if((fp=fopen("Data.txt","wt"))==NULL)
    {
        printf("open file error!\n");
        exit(0);
    }
    int *d=(int*)malloc(sizeof(int)*p);
    srand(time(NULL));
    for(i=0;i<p;i++){
        d[i]=rand()%10000;
        fprintf(fp,"%d ",d[i]);
    }
    fclose(fp);
    free(d);
}

void Read()
{
    FILE *fp;
    clock_t begintime;
    clock_t endtime;
    if((fp=fopen("Data.txt","r"))==NULL)
    {
        printf("open file error!\n");
        exit(0);
    }
    int *a=(int*)malloc(sizeof(int)*1000000);
    int num,i=0;
    while(fscanf(fp,"%d",&num)!=EOF){
        a[i]=num;
        i++;                 // iΪ����ļ��ж�������
    }
    int *d=(int*)malloc(sizeof(int)*i);
    for(int j=0;j<i;j++){
        d[j]=a[j];
    }
    printf("��ȡ���\n���ڽ����������\n\n");
    fclose(fp);
    free(a);
    begintime=clock();
    insertSort(d,i);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    int temp[i];
    begintime=clock();
    MergeSort(d,0,i-1,temp);
    endtime=clock();
    printf("�鲢�����ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    QuickSort_Recursion(d,0,i-1);
    endtime=clock();
    printf("�������򣨵ݹ�棩��ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    QuickSort(d,i);
    endtime=clock();
    printf("�������򣨷ǵݹ�棩��ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    int max=d[0];
    for(int k=1;k<i;k++){
        if(d[k]>max)
            max=d[k];
    }
    begintime=clock();
    CountSort(d,i,max);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    RadixCountSort(d,i);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    printf("\n���Խ���\n");
    getchar();
    free(d);
}

void Test(int *a,int length)
{
    int i;
    clock_t begintime;
    clock_t endtime;
    printf("\n*****����СΪ%d*****\n\n",length);
    srand(time(NULL));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    insertSort(a,length);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    int temp[length];
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    MergeSort(a,0,length-1,temp);
    endtime=clock();
    printf("�鲢�����ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    for(int i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    QuickSort_Recursion(a,0,length-1);
    endtime=clock();
    printf("�������򣨵ݹ�棩��ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    QuickSort(a,length);
    endtime=clock();
    printf("�������򣨷ǵݹ�棩��ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    int max=a[0];
    for(int j=1;j<length;j++){
        if(a[j]>max)
            max=a[j];
    }
    begintime=clock();
    CountSort(a,length,max);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    RadixCountSort(a,length);
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
}

void Test2()
{
    int i,j;
    int a[100]={0};
    clock_t begintime;
    clock_t endtime;
    printf("\n*****�Դ�СΪ100����100k������*****\n\n");
    srand(time(NULL));
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        insertSort(a,100);
    }
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    int temp[100];
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        MergeSort(a,0,99,temp);
    }
    endtime=clock();
    printf("�鲢�����ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        QuickSort_Recursion(a,0,99);
    }
    endtime=clock();
    printf("�������򣨵ݹ�棩��ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        int max=a[0];
        for(int j=1;j<100;j++){
            if(a[j]>max)
                max=a[j];
        }
        CountSort(a,100,max);
    }
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        RadixCountSort(a,100);
    }
    endtime=clock();
    printf("���������ʱ��Ϊ:%dms\n",(int)(endtime-begintime));
}
