#include"SqStack.h"
#include<stdio.h>
#include<stdlib.h>

//基于数组的顺序栈
Status initStack(SqStack *s,int sizes)//初始化栈
{
    if(s==NULL){
        printf("开辟内存失败\n");
        return ERROR;
    }
    s->top=-1;
    s->size=sizes;
    s->elem=(ElemType *)malloc(sizes*sizeof(ElemType));
    printf("初始化完成\n");
    return SUCCESS;
}

Status isEmptyStack(SqStack *s)//判断栈是否为空
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->top<0){
        printf("空栈！\n");
        return ERROR;
    }
    else
        printf("不是空栈！\n");
    return SUCCESS;
}
Status getTopStack(SqStack *s,ElemType *e) //得到栈顶元素
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->top>=0){
        *e=&s->elem[s->top];
        return SUCCESS;
    }
    else
        printf("空栈！无法获得元素！\n");
    return ERROR;
}
Status clearStack(SqStack *s)//清空栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    s->top=-1;
    printf("已清空\n");
    return SUCCESS;
}
Status destroyStack(SqStack *s)//销毁栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    free(s->elem);                    //s并不销毁
    s->top=-1;
    s->size=0;
    printf("销毁完毕\n");
    return SUCCESS;
}
Status stackLength(SqStack *s,int *length)//检测栈长度
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    *length=s->top+1;
    return SUCCESS;
}
Status pushStack(SqStack *s,ElemType data)//入栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->top<(s->size-1))
    {
        s->elem[++s->top]=data;
        printf("入栈成功！\n");
        return SUCCESS;
    }
    else
        printf("栈空间已满！\n");
    return ERROR;
}
Status popStack(SqStack *s,ElemType *data)//出栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->top>=0){
        *data=&s->elem[s->top--];
        printf("出栈成功！\n");
        return SUCCESS;
    }
    else
        printf("栈空！\n");
    return ERROR;
}
