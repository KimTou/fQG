#include"qgsort.h"
#include <stdio.h>
#include <stdlib.h>


/**
 *  @name        : void insertSort(int *a,int n);
 *  @description : ��������
 *  @param       : ����ָ�� a, ���鳤�� n
 */
void insertSort(int *a,int n)
{
    int temp,i,j;
    for(i=1;i<n;i++){          //ȡ����ǰλ�õ�ֵ��ȥ��ǰ���Ѿ��ź���ıȽ�
        temp=a[i];
        for(j=i;j>0&&a[j-1]>temp;j--){
            a[j]=a[j-1];       //����һλ
        }
        a[j]=temp;
    }
}


/**
 *  @name        : void MergeArray(int *a,int begin,int mid,int end,int *temp);
 *  @description : �鲢���򣨺ϲ����飩
 *  @param       : ����ָ��a���������begin�������е�mid�������յ�end����������ָ��temp
 */
void MergeArray(int *a,int begin,int mid,int end,int *temp)
{
    int p=0;
    int p1=begin;
    int p2=mid+1;
    while(p1<=mid&&p2<=end){
        if(a[p1]<a[p2])
            temp[p++]=a[p1++];
        else
            temp[p++]=a[p2++];
    }
    while(p1<=mid){
        temp[p++]=a[p1++];
    }
    while(p2<=end){
        temp[p++]=a[p2++];
    }
    for(int i=0;i<p;i++){
        a[begin+i]=temp[i];
    }
}


/**
 *  @name        : void MergeSort(int *a,int begin,int end,int *temp);
 *  @description : �鲢����
 *  @param       : ����ָ��a���������begin�������յ�end����������ָ��temp
 */
void MergeSort(int *a,int begin,int end,int *temp)
{
    if(begin<end){
        int mid=begin+(end-begin)/2;
        MergeSort(a,begin,mid,temp);         //�������
        MergeSort(a,mid+1,end,temp);         //�ұ�����
        MergeArray(a,begin,mid,end,temp);    //�������������кϲ�
    }
}


/**
 *  @name        : void QuickSort(int *a, int begin, int end);
 *  @description : �������򣨵ݹ�棩
 *  @param       : ����ָ��a���������begin�������յ�end
 */
void QuickSort_Recursion(int *a, int begin, int end)
{
    if(begin<end){
        int i=begin;
        int j=end;
        int monster=a[i];
        while(i<j){
            while(i<j&&a[j]>=monster)        //���������ҵ���monsterС����
                j--;
            a[i]=a[j];
            while(i<j&&a[i]<=monster)        //���������ҵ���monster�����
                i++;
            a[j]=a[i];
        }
        a[i]=monster;                        //���������ٽ��еݹ鴦��
        QuickSort_Recursion(a,begin,i-1);
        QuickSort_Recursion(a,i+1,end);
    }
    /*
    if(begin<end){
        int mid=Partition(a,begin,end);
        QuickSort_Recursion(a,begin,mid-1);        //Ҳ�������û��ֺ������
        QuickSort_Recursion(a,mid+1,end);
    }
    */
}

struct Node{
    int begin;
    int end;
};
/**
 *  @name        : void QuickSort(int *a,int size)
 *  @description : �������򣨷ǵݹ�棩
 *  @param       : ����ָ��a�����鳤��size
 */
void QuickSort(int *a,int size)
{
    int monster=a[0];
    int begin=0;
    int end=size-1;
    int temp;
    int i=begin;
    int j=end;
    struct Node stack[100];
    int top=0;
    stack[top].begin=begin;
    stack[top].end=end;
    while(top>-1){                         //ջ��Ϊ��
        i=begin=stack[top].begin;
        j=end=stack[top].end;
        top--;
        monster=a[begin];
        while(i<j){
            while(i<j&&a[j]>=monster)      //����������
                j--;
            if(i<j){
                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
                i++;
            }
            while(i<j&&a[i]<=monster)      //����������
                i++;
            if(i<j){
                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
                j--;
            }
        }
        if(begin<i-1){
            top++;
            stack[top].begin=begin;
            stack[top].end=i-1;
        }
        if(end>i+1){
            top++;
            stack[top].begin=i+1;
            stack[top].end=end;
        }
    }
    free(stack);
}


/**
 *  @name        : void QuickSort(int *a, int begin, int end)
 *  @description : �������������ţ�
 *  @param       : ����ָ��a���������begin�������յ�end
 */
int Partition(int *a, int begin, int end)
{
    if(begin<end){
        int i=begin;
        int j=end;
        int monster=a[i];
        while(i<j){
            while(i<j&&a[j]>=monster)        //���������ҵ���monsterС����
                j--;
            a[i]=a[j];
            while(i<j&&a[i]<=monster)        //���������ҵ���monster�����
                i++;
            a[j]=a[i];
        }
        a[i]=monster;
        return i;                            //������Ŧ
    }
}


/**
 *  @name        : void CountSort(int *a, int size , int max)
 *  @description : ��������
 *  @param       : ����ָ��a�����鳤��size���������ֵmax
 */
void CountSort(int *a, int size , int max)
{
    int i,j;
    int *countArray=(int*)malloc(sizeof(int)*(max+1));      //�ռ�/ͳ������
    int *sortedArray=(int*)malloc(sizeof(int)*size);        //�ź��������
    for(i=0;i<max+1;i++){
        countArray[i]=0;       //��ʼ������
    }
    for(i=0;i<size;i++){
        countArray[a[i]]++;    //�ռ�ÿ�����ֵĸ���
    }
    for(i=1;i<max+1;i++){
        countArray[i]+=countArray[i-1];        //����С�ڵ��ڸ�ֵ��Ԫ�ظ���
    }
    for(i=size-1;i>=0;i--){
        j=--countArray[a[i]];              //����ǰ�轫���϶�Ӧ��ֵ��1��������У�
        sortedArray[j]=a[i];
    }
    for(i=0;i<size;i++){
        a[i]=sortedArray[i];
    }
    free(countArray);
    free(sortedArray);
}


/**
 *  @name        : void RadixCountSort(int *a,int size)
 *  @description : ������������
 *  @param       : ����ָ��a�����鳤��size
 */
void RadixCountSort(int *a,int size)
{
    int i;
    int count[size];                  //��ʱ����
    int pos=1;
    int max=a[0];
    for(i=1;i<size;i++){
        if(a[i]>max)                  //�ҳ������
            max=a[i];
    }
    while(max/pos>0){                 //ѭ�������λ��
        int bucket[10]={0};           //�ռ�ͳ������
        for(i=0;i<size;i++){
            bucket[(a[i]/pos)%10]++;   //�ռ�
        }
        for(i=1;i<10;i++){
            bucket[i]+=bucket[i-1];     //ͳ��
        }
        for(i=size-1;i>=0;i--){
            count[--bucket[(a[i]/pos)%10]]=a[i];
        }
        for(i=0;i<size;i++){
            a[i]=count[i];
        }
        pos*=10;                        //��һλ
    }
}


/**
 *  @name        : void ColorSort(int *a,int size)
 *  @description : ��ɫ����
 *  @param       : ����ָ��a��ֻ��0��1��2Ԫ�أ������鳤��size
 */
void ColorSort(int *a,int size)
{
    int begin=0;       //����0������
    int end=size-1;    //����2������
    int p;             //��������
    int temp;
    for(p=0;p<=end;p++){       //p�������б���
        if(a[p]==0){
            temp=a[begin];
            a[begin]=a[p];
            a[p]=temp;
            begin++;
        }
        else if(a[p]==2){
            temp=a[end];
            a[end]=a[p];
            a[p]=temp;
            end--;
            p--;
        }
    }
}


/**
 *  @name        : ����
 *  @description : ��һ�������������ҵ���K��/С����
 *  @param       : ����ָ��a�����鳤��size
 */
int FindKth(int *a,int size,int k)
{
    QuickSort_Recursion(a,0,size-1);            //ͨ�����ŵĻ��ַ���������
    return a[k-1];
}

