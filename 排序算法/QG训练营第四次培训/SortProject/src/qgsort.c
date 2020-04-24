#include"qgsort.h"
#include <stdio.h>
#include <stdlib.h>


/**
 *  @name        : void insertSort(int *a,int n);
 *  @description : 插入排序
 *  @param       : 数组指针 a, 数组长度 n
 */
void insertSort(int *a,int n)
{
    int temp,i,j;
    for(i=1;i<n;i++){          //取出当前位置的值，去与前面已经排好序的比较
        temp=a[i];
        for(j=i;j>0&&a[j-1]>temp;j--){
            a[j]=a[j-1];       //后移一位
        }
        a[j]=temp;
    }
}


/**
 *  @name        : void MergeArray(int *a,int begin,int mid,int end,int *temp);
 *  @description : 归并排序（合并数组）
 *  @param       : 数组指针a，数组起点begin，数组中点mid，数组终点end，承载数组指针temp
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
 *  @description : 归并排序
 *  @param       : 数组指针a，数组起点begin，数组终点end，承载数组指针temp
 */
void MergeSort(int *a,int begin,int end,int *temp)
{
    if(begin<end){
        int mid=begin+(end-begin)/2;
        MergeSort(a,begin,mid,temp);         //左边排序
        MergeSort(a,mid+1,end,temp);         //右边排序
        MergeArray(a,begin,mid,end,temp);    //将两个有序数列合并
    }
}


/**
 *  @name        : void QuickSort(int *a, int begin, int end);
 *  @description : 快速排序（递归版）
 *  @param       : 数组指针a，数组起点begin，数组终点end
 */
void QuickSort_Recursion(int *a, int begin, int end)
{
    if(begin<end){
        int i=begin;
        int j=end;
        int monster=a[i];
        while(i<j){
            while(i<j&&a[j]>=monster)        //从右往左找到比monster小的数
                j--;
            a[i]=a[j];
            while(i<j&&a[i]<=monster)        //从左往右找到比monster大的数
                i++;
            a[j]=a[i];
        }
        a[i]=monster;                        //左右两边再进行递归处理
        QuickSort_Recursion(a,begin,i-1);
        QuickSort_Recursion(a,i+1,end);
    }
    /*
    if(begin<end){
        int mid=Partition(a,begin,end);
        QuickSort_Recursion(a,begin,mid-1);        //也可以利用划分函数完成
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
 *  @description : 快速排序（非递归版）
 *  @param       : 数组指针a，数组长度size
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
    while(top>-1){                         //栈不为空
        i=begin=stack[top].begin;
        j=end=stack[top].end;
        top--;
        monster=a[begin];
        while(i<j){
            while(i<j&&a[j]>=monster)      //处理左区间
                j--;
            if(i<j){
                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
                i++;
            }
            while(i<j&&a[i]<=monster)      //处理右区间
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
 *  @description : 快速排序（枢轴存放）
 *  @param       : 数组指针a，数组起点begin，数组终点end
 */
int Partition(int *a, int begin, int end)
{
    if(begin<end){
        int i=begin;
        int j=end;
        int monster=a[i];
        while(i<j){
            while(i<j&&a[j]>=monster)        //从右往左找到比monster小的数
                j--;
            a[i]=a[j];
            while(i<j&&a[i]<=monster)        //从左往右找到比monster大的数
                i++;
            a[j]=a[i];
        }
        a[i]=monster;
        return i;                            //返回枢纽
    }
}


/**
 *  @name        : void CountSort(int *a, int size , int max)
 *  @description : 计数排序
 *  @param       : 数组指针a，数组长度size，数组最大值max
 */
void CountSort(int *a, int size , int max)
{
    int i,j;
    int *countArray=(int*)malloc(sizeof(int)*(max+1));      //收集/统计数组
    int *sortedArray=(int*)malloc(sizeof(int)*size);        //排好序的数组
    for(i=0;i<max+1;i++){
        countArray[i]=0;       //初始化数组
    }
    for(i=0;i<size;i++){
        countArray[a[i]]++;    //收集每个数字的个数
    }
    for(i=1;i<max+1;i++){
        countArray[i]+=countArray[i-1];        //计算小于等于该值的元素个数
    }
    for(i=size-1;i>=0;i--){
        j=--countArray[a[i]];              //分配前需将表上对应的值减1（倒序进行）
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
 *  @description : 基数计数排序
 *  @param       : 数组指针a，数组长度size
 */
void RadixCountSort(int *a,int size)
{
    int i;
    int count[size];                  //临时数组
    int pos=1;
    int max=a[0];
    for(i=1;i<size;i++){
        if(a[i]>max)                  //找出最大数
            max=a[i];
    }
    while(max/pos>0){                 //循环到最大位数
        int bucket[10]={0};           //收集统计数组
        for(i=0;i<size;i++){
            bucket[(a[i]/pos)%10]++;   //收集
        }
        for(i=1;i<10;i++){
            bucket[i]+=bucket[i-1];     //统计
        }
        for(i=size-1;i>=0;i--){
            count[--bucket[(a[i]/pos)%10]]=a[i];
        }
        for(i=0;i<size;i++){
            a[i]=count[i];
        }
        pos*=10;                        //进一位
    }
}


/**
 *  @name        : void ColorSort(int *a,int size)
 *  @description : 颜色排序
 *  @param       : 数组指针a（只含0，1，2元素），数组长度size
 */
void ColorSort(int *a,int size)
{
    int begin=0;       //控制0的索引
    int end=size-1;    //控制2的索引
    int p;             //遍历索引
    int temp;
    for(p=0;p<=end;p++){       //p索引进行遍历
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
 *  @name        : 自拟
 *  @description : 在一个无序序列中找到第K大/小的数
 *  @param       : 数组指针a，数组长度size
 */
int FindKth(int *a,int size,int k)
{
    QuickSort_Recursion(a,0,size-1);            //通过快排的划分法重新排序
    return a[k-1];
}

