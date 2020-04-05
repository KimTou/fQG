#include"LinkStack.h"
#include <stdio.h>
#include <stdlib.h>

Status initLStack(LinkStack *s)//初始化栈
{
    if(s==NULL){
        printf("开辟内存失败\n");
        return ERROR;
    }
    s->count=0;
    s->top=NULL;
    printf("初始化完成！\n");
    return SUCCESS;
}
Status isEmptyLStack(LinkStack *s)//判断栈是否为空
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->count==0){                 //用if(s->top==NULL)也行
        printf("空栈\n");
        return ERROR;
    }
    else
        printf("栈不是空的\n");
    return SUCCESS;
}
Status getTopLStack(LinkStack *s,ElemType *e)//得到栈顶元素
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->count==0){                      //用if(s->top==NULL)也行
        printf("空栈！无法获取元素！\n");
        return ERROR;
    }
    else
        *e=&s->top->data;
        return SUCCESS;
}
Status clearLStack(LinkStack *s)//清空栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->count==0){                      //用if(s->top==NULL)也行
        printf("已经是空栈了！\n");
        return ERROR;
    }
    s->count=0;
    s->top=NULL;
    printf("已清空\n");
    return SUCCESS;
}
Status destroyLStack(LinkStack *s)//销毁栈
{
    LinkStackPtr p;
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    while(s->top!=NULL){
        p=s->top;
        s->top=p->next;           //s并不销毁
        free(p);
    }
    s->count=0;
    printf("销毁完成\n");
    return SUCCESS;
}
Status LStackLength(LinkStack *s,int *length)//检测栈长度
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    *length=s->count;
    return SUCCESS;
}
Status pushLStack(LinkStack *s,ElemType data)//入栈
{
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    LinkStackPtr p;
    p=(LinkStackPtr)malloc(sizeof(StackNode));
    if(p==NULL){
        printf("入栈失败\n");
        return ERROR;
    }
    p->data=data;
    p->next=s->top;
    s->top=p;
    s->count++;
    printf("入栈成功！\n");
    return SUCCESS;
}
Status popLStack(LinkStack *s,ElemType *data)//出栈
{
    LinkStackPtr p;
    if(s==NULL){
        printf("请先初始化栈！\n");
        return ERROR;
    }
    if(s->count==0){                      //用if(s->top==NULL)也行
        printf("空栈！无法弹出元素！\n");
        return ERROR;
    }
    *data=s->top->data;
    p=s->top;
    s->top=p->next;
    free(p);
    s->count--;
    printf("出栈完成！\n");
    return SUCCESS;
}
