#include"qgsort.h"
#include <stdio.h>
#include <stdlib.h>
#include<time.h>

int main()
{
    //Add();                  //测试数据生成程序
    Read();                   //读取测试数据并测试排序函数的程序
/*
    int a[10000]={0};
    Test(a,10000);
    int b[50000]={0};
    Test(b,50000);             //大数据量与大量小数组的排序测试程序
    int static c[200000] ;
    Test(c,200000);
    Test2();
*/
    return 0;
}

void Add()
{
    int i,j,c,z,p;       //防止输入不是整数
    char a[20];
    printf("请输入你想生成多大的数据量到txt文件(1~1000000)：\n");
    while(1){
        scanf("%s",a);
        for(i=0,j=0;i<strlen(a);i++)                  //遍历整个字符串
        {
            if(a[i]<=57&&a[i]>=48)                  //0~9的ASCII码是48~57
                j++;
        }
        if(j==strlen(a)){                           //防止传进来不是整数
            p=0;
            for(c=1;j>0;j--){
                z=(int)a[j-1]-48;
                p+=z*c;
                c*=10;
            }
        if(p>0&&p<1000000)
            break;
        else
            printf("请正确输入整数！\n重新输入：");
        }
        else
            printf("请正确输入整数！\n重新输入：");
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
        i++;                 // i为这个文件有多少数字
    }
    int *d=(int*)malloc(sizeof(int)*i);
    for(int j=0;j<i;j++){
        d[j]=a[j];
    }
    printf("读取完毕\n正在进行排序测试\n\n");
    fclose(fp);
    free(a);
    begintime=clock();
    insertSort(d,i);
    endtime=clock();
    printf("插入排序的时间为:%dms\n",(int)(endtime-begintime));
    int temp[i];
    begintime=clock();
    MergeSort(d,0,i-1,temp);
    endtime=clock();
    printf("归并排序的时间为:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    QuickSort_Recursion(d,0,i-1);
    endtime=clock();
    printf("快速排序（递归版）的时间为:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    QuickSort(d,i);
    endtime=clock();
    printf("快速排序（非递归版）的时间为:%dms\n",(int)(endtime-begintime));
    int max=d[0];
    for(int k=1;k<i;k++){
        if(d[k]>max)
            max=d[k];
    }
    begintime=clock();
    CountSort(d,i,max);
    endtime=clock();
    printf("计数排序的时间为:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    RadixCountSort(d,i);
    endtime=clock();
    printf("基数排序的时间为:%dms\n",(int)(endtime-begintime));
    printf("\n测试结束\n");
    getchar();
    free(d);
}

void Test(int *a,int length)
{
    int i;
    clock_t begintime;
    clock_t endtime;
    printf("\n*****当大小为%d*****\n\n",length);
    srand(time(NULL));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    insertSort(a,length);
    endtime=clock();
    printf("插入排序的时间为:%dms\n",(int)(endtime-begintime));
    int temp[length];
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    MergeSort(a,0,length-1,temp);
    endtime=clock();
    printf("归并排序的时间为:%dms\n",(int)(endtime-begintime));
    for(int i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    QuickSort_Recursion(a,0,length-1);
    endtime=clock();
    printf("快速排序（递归版）的时间为:%dms\n",(int)(endtime-begintime));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    QuickSort(a,length);
    endtime=clock();
    printf("快速排序（非递归版）的时间为:%dms\n",(int)(endtime-begintime));
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
    printf("计数排序的时间为:%dms\n",(int)(endtime-begintime));
    for(i=0;i<length;i++){
        a[i]=rand()%100000;
    }
    begintime=clock();
    RadixCountSort(a,length);
    endtime=clock();
    printf("基数排序的时间为:%dms\n",(int)(endtime-begintime));
}

void Test2()
{
    int i,j;
    int a[100]={0};
    clock_t begintime;
    clock_t endtime;
    printf("\n*****对大小为100进行100k次排序*****\n\n");
    srand(time(NULL));
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        insertSort(a,100);
    }
    endtime=clock();
    printf("插入排序的时间为:%dms\n",(int)(endtime-begintime));
    int temp[100];
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        MergeSort(a,0,99,temp);
    }
    endtime=clock();
    printf("归并排序的时间为:%dms\n",(int)(endtime-begintime));
    begintime=clock();
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        QuickSort_Recursion(a,0,99);
    }
    endtime=clock();
    printf("快速排序（递归版）的时间为:%dms\n",(int)(endtime-begintime));
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
    printf("计数排序的时间为:%dms\n",(int)(endtime-begintime));
    for(i=0;i<999999;i++){
        for(j=0;j<99;j++)
            a[j]=rand()%1000;
        RadixCountSort(a,100);
    }
    endtime=clock();
    printf("基数排序的时间为:%dms\n",(int)(endtime-begintime));
}
