#include"LinkStack.h"
#include <stdio.h>
#include <stdlib.h>

Status initLStack(LinkStack *s)//��ʼ��ջ
{
    if(s==NULL){
        printf("�����ڴ�ʧ��\n");
        return ERROR;
    }
    s->count=0;
    s->top=NULL;
    printf("��ʼ����ɣ�\n");
    return SUCCESS;
}
Status isEmptyLStack(LinkStack *s)//�ж�ջ�Ƿ�Ϊ��
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->count==0){                 //��if(s->top==NULL)Ҳ��
        printf("��ջ\n");
        return ERROR;
    }
    else
        printf("ջ���ǿյ�\n");
    return SUCCESS;
}
Status getTopLStack(LinkStack *s,ElemType *e)//�õ�ջ��Ԫ��
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->count==0){                      //��if(s->top==NULL)Ҳ��
        printf("��ջ���޷���ȡԪ�أ�\n");
        return ERROR;
    }
    else
        *e=&s->top->data;
        return SUCCESS;
}
Status clearLStack(LinkStack *s)//���ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->count==0){                      //��if(s->top==NULL)Ҳ��
        printf("�Ѿ��ǿ�ջ�ˣ�\n");
        return ERROR;
    }
    s->count=0;
    s->top=NULL;
    printf("�����\n");
    return SUCCESS;
}
Status destroyLStack(LinkStack *s)//����ջ
{
    LinkStackPtr p;
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    while(s->top!=NULL){
        p=s->top;
        s->top=p->next;           //s��������
        free(p);
    }
    s->count=0;
    printf("�������\n");
    return SUCCESS;
}
Status LStackLength(LinkStack *s,int *length)//���ջ����
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    *length=s->count;
    return SUCCESS;
}
Status pushLStack(LinkStack *s,ElemType data)//��ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    LinkStackPtr p;
    p=(LinkStackPtr)malloc(sizeof(StackNode));
    if(p==NULL){
        printf("��ջʧ��\n");
        return ERROR;
    }
    p->data=data;
    p->next=s->top;
    s->top=p;
    s->count++;
    printf("��ջ�ɹ���\n");
    return SUCCESS;
}
Status popLStack(LinkStack *s,ElemType *data)//��ջ
{
    LinkStackPtr p;
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->count==0){                      //��if(s->top==NULL)Ҳ��
        printf("��ջ���޷�����Ԫ�أ�\n");
        return ERROR;
    }
    *data=s->top->data;
    p=s->top;
    s->top=p->next;
    free(p);
    s->count--;
    printf("��ջ��ɣ�\n");
    return SUCCESS;
}
