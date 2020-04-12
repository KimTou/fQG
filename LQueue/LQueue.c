#include"LQueue.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int hpos;       //��¼��ͷ��λ��
int npos=0;
/**
 *  @name        : void InitLQueue(LQueue *Q)
 *    @description : ��ʼ������
 *    @param         Q ����ָ��Q
 *  @notice      : None
 */
void InitLQueue(LQueue *Q)
{
    Node *p=(Node*)malloc(sizeof(Node));
    if(p==NULL){
        printf("��ʼ��ʧ�ܣ�\n");
    }
    p->next=NULL;        //����ͷָ��
    Q->front=Q->rear=p;
    Q->length=0;
    hpos=npos;            //Ϊ������֮���ܹ��ٽ�ͷ������������������β��һλ����������
    printf("��ʼ���ɹ���\n");
}

/**
 *  @name        : void DestoryLQueue(LQueue *Q)
 *    @description : ���ٶ���
 *    @param         Q ����ָ��Q
 *  @notice      : None
 */
void DestoryLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("���ȳ�ʼ�����У�\n");
        return ;
    }
    Node *p,*q;         //����Ҫ�ٿ��ٿռ�
    p=Q->front;
    while(p!=NULL){
        q=p->next;
        free(p);
        p=q;
    }
    Q->front=NULL;
    Q->rear=NULL;
    printf("���ٳɹ���\n");
}

/**
 *  @name        : Status IsEmptyLQueue(const LQueue *Q)
 *    @description : �������Ƿ�Ϊ��
 *    @param         Q ����ָ��Q
 *    @return         : ��-TRUE; δ��-FALSE
 *  @notice      : None
 */
Status IsEmptyLQueue(const LQueue *Q)
{
    if(Q->front==Q->rear){     //����Q->length==0
        return TRUE;
    }
    return FALSE;
}

/**
 *  @name        : Status GetHeadLQueue(LQueue *Q, void *e)
 *    @description : �鿴��ͷԪ��
 *    @param         Q e ����ָ��Q,��������ָ��e
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : �����Ƿ��
 */
Status GetHeadLQueue(LQueue *Q, void *e)
{
    memcpy(e,Q->front->next->data,30);   //memcpy()��Q->front->next->data����e����������Ϊvoid*��
    return TRUE;
}

/**
 *  @name        : int LengthLQueue(LQueue *Q)
 *    @description : ȷ�����г���
 *    @param         Q ����ָ��Q
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : None
 */
int LengthLQueue(LQueue *Q)
{
    return Q->length;
}

/**
 *  @name        : Status EnLQueue(LQueue *Q, void *data)
 *    @description : ��Ӳ���
 *    @param         Q ����ָ��Q,�������ָ��data
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : �����Ƿ�Ϊ��
 */
Status EnLQueue(LQueue *Q, void *data)
{
    Node *p=(Node*)malloc(sizeof(Node));
    if(p==NULL){
        printf("���ʧ�ܣ�\n");
        return FALSE;
    }
    p->data=(void*)malloc(30);      //p->dataҲҪmalloc������
    memcpy(p->data,data,30);
    p->next=NULL;
    Q->rear->next=p;
    Q->rear=p;              //Ҳ����Ҫ��npos++
    Q->length++;
    return TRUE;
}

/**
 *  @name        : Status DeLQueue(LQueue *Q)
 *    @description : ���Ӳ���
 *    @param         Q ����ָ��Q
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : None
 */
Status DeLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("���ȳ�ʼ�����У�\n");
        return FALSE;
    }
    if(IsEmptyLQueue(Q)){
        printf("����Ϊ�գ�\n");
        return FALSE;
    }
    Node *p=(Node*)malloc(sizeof(Node));
    p=Q->front->next;
    Q->front->next=p->next;
    free(p);
    if(Q->front->next==NULL){
        Q->rear=Q->front;      //�����иպ�ֻʣһ��Ԫ�أ�����к���Ϊ�գ�Q->rear�պñ�free������Ҫ���¶���
    }
    hpos++;        //��ͷλ�����һ��
    Q->length--;
    printf("���ӳɹ���\n");
    return TRUE;
}

/**
 *  @name        : void ClearLQueue(AQueue *Q)
 *    @description : ��ն���
 *    @param         Q ����ָ��Q
 *  @notice      : None
 */
void ClearLQueue(LQueue *Q)
{
    if(Q==NULL){
        printf("���ȳ�ʼ�����У�\n");
        return ;
    }
    if(IsEmptyLQueue(Q)){
        printf("�����Ѿ�Ϊ�գ�\n");
        return ;
    }
    Q->front->next=NULL;
    Q->rear=Q->front;
    hpos=npos;
    Q->length=0;
    printf("�����ɣ�\n");
}

/**
 *  @name        : Status TraverseLQueue(const LQueue *Q, void (*foo)(void *q))
 *    @description : ������������
 *    @param         Q ����ָ��Q����������ָ��foo
 *    @return         : None
 *  @notice      : None
 */
Status TraverseLQueue(const LQueue *Q, void (*foo)(void *q))
{
    if(Q==NULL){
        printf("���ȳ�ʼ�����У�\n");
        return FALSE;
    }
    if(IsEmptyLQueue(Q)){
        printf("����Ϊ�գ�\n");
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
 *    @description : ��������
 *    @param         q ָ��q

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
