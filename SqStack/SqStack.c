#include"SqStack.h"
#include<stdio.h>
#include<stdlib.h>

//���������˳��ջ
Status initStack(SqStack *s,int sizes)//��ʼ��ջ
{
    if(s==NULL){
        printf("�����ڴ�ʧ��\n");
        return ERROR;
    }
    s->top=-1;
    s->size=sizes;
    s->elem=(ElemType *)malloc(sizes*sizeof(ElemType));
    printf("��ʼ�����\n");
    return SUCCESS;
}

Status isEmptyStack(SqStack *s)//�ж�ջ�Ƿ�Ϊ��
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->top<0){
        printf("��ջ��\n");
        return ERROR;
    }
    else
        printf("���ǿ�ջ��\n");
    return SUCCESS;
}
Status getTopStack(SqStack *s,ElemType *e) //�õ�ջ��Ԫ��
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->top>=0){
        *e=&s->elem[s->top];
        return SUCCESS;
    }
    else
        printf("��ջ���޷����Ԫ�أ�\n");
    return ERROR;
}
Status clearStack(SqStack *s)//���ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    s->top=-1;
    printf("�����\n");
    return SUCCESS;
}
Status destroyStack(SqStack *s)//����ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    free(s->elem);                    //s��������
    s->top=-1;
    s->size=0;
    printf("�������\n");
    return SUCCESS;
}
Status stackLength(SqStack *s,int *length)//���ջ����
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    *length=s->top+1;
    return SUCCESS;
}
Status pushStack(SqStack *s,ElemType data)//��ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->top<(s->size-1))
    {
        s->elem[++s->top]=data;
        printf("��ջ�ɹ���\n");
        return SUCCESS;
    }
    else
        printf("ջ�ռ�������\n");
    return ERROR;
}
Status popStack(SqStack *s,ElemType *data)//��ջ
{
    if(s==NULL){
        printf("���ȳ�ʼ��ջ��\n");
        return ERROR;
    }
    if(s->top>=0){
        *data=&s->elem[s->top--];
        printf("��ջ�ɹ���\n");
        return SUCCESS;
    }
    else
        printf("ջ�գ�\n");
    return ERROR;
}
