#include"LQueue.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int hpos;       //记录队头的位置
int npos=0;
/**
 *  @name        : void InitLQueue(LQueue *Q)
 *    @description : 初始化队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void InitLQueue(LQueue *Q)
{
    Node *p=(Node*)malloc(sizeof(Node));
    if(p==NULL){
        printf("初始化失败！\n");
    }
    p->next=NULL;        //创建头指针
    Q->front=Q->rear=p;
    Q->length=0;
    hpos=npos;            //为了销毁之后能够再将头的数据类型锁定到队尾后一位的数据类型
    printf("初始化成功！\n");
}

/**
 *  @name        : void DestoryLQueue(LQueue *Q)
 *    @description : 销毁队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void DestoryLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("请先初始化队列！\n");
        return ;
    }
    Node *p,*q;         //不需要再开辟空间
    p=Q->front;
    while(p!=NULL){
        q=p->next;
        free(p);
        p=q;
    }
    Q->front=NULL;
    Q->rear=NULL;
    printf("销毁成功！\n");
}

/**
 *  @name        : Status IsEmptyLQueue(const LQueue *Q)
 *    @description : 检查队列是否为空
 *    @param         Q 队列指针Q
 *    @return         : 空-TRUE; 未空-FALSE
 *  @notice      : None
 */
Status IsEmptyLQueue(const LQueue *Q)
{
    if(Q->front==Q->rear){     //或者Q->length==0
        return TRUE;
    }
    return FALSE;
}

/**
 *  @name        : Status GetHeadLQueue(LQueue *Q, void *e)
 *    @description : 查看队头元素
 *    @param         Q e 队列指针Q,返回数据指针e
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : 队列是否空
 */
Status GetHeadLQueue(LQueue *Q, void *e)
{
    memcpy(e,Q->front->next->data,30);   //memcpy()将Q->front->next->data赋给e，且两个均为void*型
    return TRUE;
}

/**
 *  @name        : int LengthLQueue(LQueue *Q)
 *    @description : 确定队列长度
 *    @param         Q 队列指针Q
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : None
 */
int LengthLQueue(LQueue *Q)
{
    return Q->length;
}

/**
 *  @name        : Status EnLQueue(LQueue *Q, void *data)
 *    @description : 入队操作
 *    @param         Q 队列指针Q,入队数据指针data
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : 队列是否为空
 */
Status EnLQueue(LQueue *Q, void *data)
{
    Node *p=(Node*)malloc(sizeof(Node));
    if(p==NULL){
        printf("入队失败！\n");
        return FALSE;
    }
    p->data=(void*)malloc(30);      //p->data也要malloc！！！
    memcpy(p->data,data,30);
    p->next=NULL;
    Q->rear->next=p;
    Q->rear=p;              //也不需要加npos++
    Q->length++;
    return TRUE;
}

/**
 *  @name        : Status DeLQueue(LQueue *Q)
 *    @description : 出队操作
 *    @param         Q 队列指针Q
 *    @return         : 成功-TRUE; 失败-FALSE
 *  @notice      : None
 */
Status DeLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("请先初始化队列！\n");
        return FALSE;
    }
    if(IsEmptyLQueue(Q)){
        printf("队列为空！\n");
        return FALSE;
    }
    Node *p=(Node*)malloc(sizeof(Node));
    p=Q->front->next;
    Q->front->next=p->next;
    free(p);
    if(Q->front->next==NULL){
        Q->rear=Q->front;      //若队列刚好只剩一个元素，则出列后则为空，Q->rear刚好被free掉，需要重新定向
    }
    hpos++;        //队头位置向后一步
    Q->length--;
    printf("出队成功！\n");
    return TRUE;
}

/**
 *  @name        : void ClearLQueue(AQueue *Q)
 *    @description : 清空队列
 *    @param         Q 队列指针Q
 *  @notice      : None
 */
void ClearLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("请先初始化队列！\n");
        return ;
    }
    if(IsEmptyLQueue(Q)){
        printf("队列已经为空！\n");
        return ;
    }
    Q->front->next=NULL;
    Q->rear=Q->front;
    hpos=npos;
    Q->length=0;
    printf("清空完成！\n");
}

/**
 *  @name        : Status TraverseLQueue(const LQueue *Q, void (*foo)(void *q))
 *    @description : 遍历函数操作
 *    @param         Q 队列指针Q，操作函数指针foo
 *    @return         : None
 *  @notice      : None
 */
Status TraverseLQueue(const LQueue *Q, void (*foo)(void *q))
{
    if(Q==NULL){
        printf("请先初始化队列！\n");
        return FALSE;
    }
    if(IsEmptyLQueue(Q)){
        printf("队列为空！\n");
        return FALSE;
    }
    //Node *p=(Node*)malloc(sizeof(Node));
    Node *p=Q->front->next;
    npos=hpos;
    while(p!=NULL){
        foo(p->data);
        p=p->next;
    }
    return TRUE;
}

/**
 *  @name        : void LPrint(void *q)
 *    @description : 操作函数
 *    @param         q 指针q

 *  @notice      : None
 */
void LPrint(void *q)
{
    if(datatype[npos]=='i'){
        printf("%d -> ",*(int*)q);
    }
    if(datatype[npos]=='f'){
        printf("%.4lf -> ",*(double*)q);
    }
    if(datatype[npos]=='c'){
        printf("%c -> ",*(char*)q);
    }
    if(datatype[npos]=='s'){
        printf("%s -> ",(char*)q);
    }
    npos++;
}
