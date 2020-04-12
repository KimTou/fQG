#include "AQueue.h"
#include <stdio.h>
#include <stdlib.h>
#include<string.h>

/**
 *  @name        : void InitAQueue(AQueue *Q)
 *    @description : 初始化队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void InitAQueue(AQueue *Q)
{
    int i;
    Q->length=MAXQUEUE;
    for(i=0;i<Q->length;i++){
        Q->data[i]=(void*)malloc(30);
    }
    Q->front=Q->rear=0;
    printf("初始化成功！\n");
}

/**
 *  @name        : void DestoryAQueue(AQueue *Q)
 *    @description : 销毁队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void DestoryAQueue(AQueue *Q)
{
    int i;
    for(i=0;i<Q->length;i++){
        free(Q->data[i]);
    }
    Q->data[0]=NULL;
    printf("已销毁\n");
}


/**
 *  @name        : Status IsFullAQueue(const AQueue *Q)
 *    @description : 检查队列是否已满
 *    @param         Q 队列指针Q
 *    @return         : 满-TRUE; 未满-FALSE
 *  @notice      : None
 */
Status IsFullAQueue(const AQueue *Q)
{
    if(LengthAQueue(Q)==Q->length-1){       //最多可存下9个元素
        return TRUE;
    }
    else
        return FALSE;
}

/**
 *  @name        : Status IsEmptyAQueue(const AQueue *Q)
 *    @description : 检查队列是否为空
 *    @param         Q 队列指针Q
 *    @return         : 空-TRUE; 未空-FALSE
 *  @notice      : None
 */
Status IsEmptyAQueue(const AQueue *Q)
{
    if(Q->rear-Q->front==0){         //或者用LengthAQueue(Q)==0
        return TRUE;
    }
    return FALSE;
}



/**
 *  @name        : Status GetHeadAQueue(AQueue *Q, void *e)
 *    @description : 查看队头元素
 *    @param         Q 队列指针Q，存放元素e
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : 队列是否空
 */
Status GetHeadAQueue(AQueue *Q, void *e)
{
    memcpy(e,Q->data[Q->front],30);
    return TRUE;
}



/**
 *  @name        : int LengthAQueue(AQueue *Q)
 *    @description : 确定队列长度
 *    @param         Q 队列指针Q
 *    @return         : 队列长度count
 *  @notice      : None
 */
int LengthAQueue(AQueue *Q)
{
    return (Q->rear-Q->front+Q->length)%Q->length;       //长度等于末尾减头的绝对值
}


/**
 *  @name        : Status EnAQueue(AQueue *Q, void *data)
 *    @description : 入队操作
 *    @param         Q 队列指针Q,入队数据指针data
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : 队列是否满了或为空
 */
Status EnAQueue(AQueue *Q, void *data)
{
    if(IsFullAQueue(Q)){
        printf("队列已满，不能再入列了！\n");
        return FALSE;
    }
    if(IsEmptyAQueue(Q)){
        memcpy(Q->data[Q->front],data,30);
        Q->rear=(Q->rear+1)%Q->length;
        printf("入队成功！\n");
        return TRUE;
    }    //memcpy():从源data所指的内存地址的起始位置开始拷贝30个字节到目标Q->data[Q->rear]所指的内存地址的起始位置中
    memcpy(Q->data[Q->rear],data,30);
    Q->rear=(Q->rear+1)%Q->length;           //若超过最大容量则循环回到头部
    printf("入队成功！\n");
    return TRUE;
}



/**
 *  @name        : Status DeAQueue(AQueue *Q)
 *    @description : 出队操作
 *    @param         Q 队列指针Q
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : None
 */
Status DeAQueue(AQueue *Q)
{
    if(IsEmptyAQueue(Q)){
        printf("队列为空！\n");
        return FALSE;
    }
    Q->front=(Q->front+1)%Q->length;
    printf("出队成功！\n");
    return TRUE;
}



/**
 *  @name        : void ClearAQueue(Queue *Q)
 *    @description : 清空队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void ClearAQueue(AQueue *Q)
{
    if(IsEmptyAQueue(Q)){
        printf("队列已经是空的了！\n");
        return ;
    }
    Q->front=Q->rear=0;
    printf("清空完成！\n");
}



/**
 *  @name        : Status TraverseAQueue(const AQueue *Q, void (*foo)(void *q))
 *    @description : 遍历函数操作
 *    @param         Q 队列指针Q，操作函数指针foo
 *    @return         : None
 *  @notice      : None
 */
int pos;       //一切都是为了能定位到datatype数组的位置
int n;           //保证Aprint能获取到位置
Status TraverseAQueue(const AQueue *Q, void (*foo)(void *q))
{
    pos=Q->front;
    n=Q->rear-1;
    if(IsEmptyAQueue(Q)){
        printf("队列为空！\n");
        return FALSE;
    }
    int i=0;
    while(i<LengthAQueue(Q)){         //从队头到队尾遍历
        foo(Q->data[(Q->front+i)%Q->length]);
        i++;
    }
}



/**
 *  @name        : void APrint(void *q)
 *    @description : 操作函数
 *    @param         q 指针q
 *  @notice      : None
 */
void APrint(void *q)
{
    if(pos!=n){
        if(datatype[pos]=='i'){
            printf("%d -- ",*(int*)q);
        }
        if(datatype[pos]=='f'){
            printf("%.4lf -- ",*(double*)q);
        }
        if(datatype[pos]=='c'){
            printf("%c -- ",*(char*)q);
        }
        if(datatype[pos]=='s'){
            printf("%s -- ",(char*)q);
        }
    }
    else{
        if(datatype[pos]=='i'){
            printf("%d\n",*(int*)q);
        }
        if(datatype[pos]=='f'){
            printf("%.4lf\n",*(double*)q);
        }
        if(datatype[pos]=='c'){
            printf("%c\n",*(char*)q);
        }
        if(datatype[pos]=='s'){
            printf("%s\n",(char*)q);
        }
    }
     pos=(pos+1)%(MAXQUEUE);
}
