#include "AQueue.h"
#include <stdio.h>
#include <stdlib.h>
#include<string.h>

/**
 *  @name        : void InitAQueue(AQueue *Q)
 *    @description : ��ʼ������
 *    @param         Q ����ָ��Q
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
    printf("��ʼ���ɹ���\n");
}

/**
 *  @name        : void DestoryAQueue(AQueue *Q)
 *    @description : ���ٶ���
 *    @param         Q ����ָ��Q
 *  @notice      : None
 */
void DestoryAQueue(AQueue *Q)
{
    int i;
    for(i=0;i<Q->length;i++){
        free(Q->data[i]);
    }
    Q->data[0]=NULL;
    printf("������\n");
}


/**
 *  @name        : Status IsFullAQueue(const AQueue *Q)
 *    @description : �������Ƿ�����
 *    @param         Q ����ָ��Q
 *    @return         : ��-TRUE; δ��-FALSE
 *  @notice      : None
 */
Status IsFullAQueue(const AQueue *Q)
{
    if(LengthAQueue(Q)==Q->length-1){       //���ɴ���9��Ԫ��
        return TRUE;
    }
    else
        return FALSE;
}

/**
 *  @name        : Status IsEmptyAQueue(const AQueue *Q)
 *    @description : �������Ƿ�Ϊ��
 *    @param         Q ����ָ��Q
 *    @return         : ��-TRUE; δ��-FALSE
 *  @notice      : None
 */
Status IsEmptyAQueue(const AQueue *Q)
{
    if(Q->rear-Q->front==0){         //������LengthAQueue(Q)==0
        return TRUE;
    }
    return FALSE;
}



/**
 *  @name        : Status GetHeadAQueue(AQueue *Q, void *e)
 *    @description : �鿴��ͷԪ��
 *    @param         Q ����ָ��Q�����Ԫ��e
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : �����Ƿ��
 */
Status GetHeadAQueue(AQueue *Q, void *e)
{
    memcpy(e,Q->data[Q->front],30);
    return TRUE;
}



/**
 *  @name        : int LengthAQueue(AQueue *Q)
 *    @description : ȷ�����г���
 *    @param         Q ����ָ��Q
 *    @return         : ���г���count
 *  @notice      : None
 */
int LengthAQueue(AQueue *Q)
{
    return (Q->rear-Q->front+Q->length)%Q->length;       //���ȵ���ĩβ��ͷ�ľ���ֵ
}


/**
 *  @name        : Status EnAQueue(AQueue *Q, void *data)
 *    @description : ��Ӳ���
 *    @param         Q ����ָ��Q,�������ָ��data
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : �����Ƿ����˻�Ϊ��
 */
Status EnAQueue(AQueue *Q, void *data)
{
    if(IsFullAQueue(Q)){
        printf("���������������������ˣ�\n");
        return FALSE;
    }
    if(IsEmptyAQueue(Q)){
        memcpy(Q->data[Q->front],data,30);
        Q->rear=(Q->rear+1)%Q->length;
        printf("��ӳɹ���\n");
        return TRUE;
    }    //memcpy():��Դdata��ָ���ڴ��ַ����ʼλ�ÿ�ʼ����30���ֽڵ�Ŀ��Q->data[Q->rear]��ָ���ڴ��ַ����ʼλ����
    memcpy(Q->data[Q->rear],data,30);
    Q->rear=(Q->rear+1)%Q->length;           //���������������ѭ���ص�ͷ��
    printf("��ӳɹ���\n");
    return TRUE;
}



/**
 *  @name        : Status DeAQueue(AQueue *Q)
 *    @description : ���Ӳ���
 *    @param         Q ����ָ��Q
 *    @return         : �ɹ�-TRUE; ʧ��-FALSE
 *  @notice      : None
 */
Status DeAQueue(AQueue *Q)
{
    if(IsEmptyAQueue(Q)){
        printf("����Ϊ�գ�\n");
        return FALSE;
    }
    Q->front=(Q->front+1)%Q->length;
    printf("���ӳɹ���\n");
    return TRUE;
}



/**
 *  @name        : void ClearAQueue(Queue *Q)
 *    @description : ��ն���
 *    @param         Q ����ָ��Q
 *  @notice      : None
 */
void ClearAQueue(AQueue *Q)
{
    if(IsEmptyAQueue(Q)){
        printf("�����Ѿ��ǿյ��ˣ�\n");
        return ;
    }
    Q->front=Q->rear=0;
    printf("�����ɣ�\n");
}



/**
 *  @name        : Status TraverseAQueue(const AQueue *Q, void (*foo)(void *q))
 *    @description : ������������
 *    @param         Q ����ָ��Q����������ָ��foo
 *    @return         : None
 *  @notice      : None
 */
int pos;       //һ�ж���Ϊ���ܶ�λ��datatype�����λ��
int n;           //��֤Aprint�ܻ�ȡ��λ��
Status TraverseAQueue(const AQueue *Q, void (*foo)(void *q))
{
    pos=Q->front;
    n=Q->rear-1;
    if(IsEmptyAQueue(Q)){
        printf("����Ϊ�գ�\n");
        return FALSE;
    }
    int i=0;
    while(i<LengthAQueue(Q)){         //�Ӷ�ͷ����β����
        foo(Q->data[(Q->front+i)%Q->length]);
        i++;
    }
}



/**
 *  @name        : void APrint(void *q)
 *    @description : ��������
 *    @param         q ָ��q
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
