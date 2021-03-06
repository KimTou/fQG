#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include<string.h>
#include"BiTree.h"
#define LEN 200

typedef struct SqStack
{
	char *fh;
	int top;
	int size;
} SqStack;

void initStack(SqStack *s)//初始化栈
{
    s->fh=(char*)malloc(LEN*sizeof(char));
    s->top=-1;
    s->size=LEN;
}

void push(SqStack *s,char fh)//入栈
{
    if(s->top<(s->size-1))
    {
        s->fh[++s->top]=fh;
    }
    else
        printf("栈空间不足！\n");
}
char pop(SqStack *s)//出栈
{
    return s->fh[s->top--];
}
char TopStack(SqStack *s) //得到栈顶元素
{
    return s->fh[s->top];
}

void houzhui(char *a,char *nb,char *qz)  //将中缀表达式转化为后缀表达式
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));          //必须要在这里初始化
    initStack(s);
    int i,j,v;
    int len=strlen(a);
    for(i=len-1,j=0;i>=0;i--)
    {
        if(a[i]>='0'&&a[i]<='9'){
            v=0;
            while(a[i]>='0'&&a[i]<='9'){
                v*=10;
                v+=(a[i]-'0');             //为了正确识别多位数
                i++;
            }
            nb[j++]=(v+'0');              //数字，直接输出.
            i--;
        }
        else {
            switch(a[i]){
                case '+':       //栈顶的运算符出栈，直到栈顶元素为左括号或空，最后将当前运算符入栈；
                case '-':
                    while(TopStack(s)!=')'&&s->top>=0){
                        nb[j++]=pop(s);
                    }
                    push(s,a[i]);
                    break;
                case '*':        //if (优先级大于栈顶运算符 ||栈顶为左括号)   入栈
                case '/':
                    if(TopStack(s)=='+'||TopStack(s)=='-'||TopStack(s)==')'||s->top==-1){
                        push(s,a[i]);
                        break;
                    }
                    if(TopStack(s)=='*'||TopStack(s)=='/'){
                        while(TopStack(s)!=')'&&s->top>=0){
                                nb[j++]=pop(s);
                        }
                        push(s,a[i]);
                        break;
                    }

                case ')':   //左括号与右括号互换
                    push(s,a[i]);       //左括号，直接入栈
                    break;
                case '(':
                    while(TopStack(s)!=')'){
                            nb[j++]=pop(s);   //右括号，将栈顶的运算符出栈，出栈再出栈，直到左括号。括号不作输出
                          }
                          pop(s);
                          break;
                default:
                    break;
            }
        }
    }
    while(s->top!=-1){
        nb[j++]=pop(s);          //剩下的全部出栈
    }
    nb[j]='\0';
    int q;
    for(q=0;q<j;q++){
        qz[q]=nb[j-q-1];          //转为前缀
    }
    qz[q]='\0';
}

int main()
{
    struct BiTNode *T;
    SqStack *n=NULL;
    char *qz=NULL;
    n=(SqStack*)malloc(sizeof(SqStack));
    initStack(n);
    int i,rs,p,cz,x;
    char a[50],nb[50],ch;
    printf("	*********************************************************\n");
    printf("	*			1:进行运算\n");
	printf("	*			2:退出计算器\n");
    printf("	*********************************************************\n");
    printf("输入操作符1-2:");
	scanf("%d", &cz);
	start:
    printf("请输入中缀表达式：\n");
    fflush(stdin);      //把缓冲区的'\n'去掉
    ch=getchar();
    for(i=0,x=0;ch!='\n';){
        if((ch>='0'&&ch<='9')||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'){
            if(ch=='-'&&a[i-1]=='-'){
                a[--i]='+';
                i++;
                ch=getchar();
                continue;
            }
            if((ch>='0'&ch<='9')&a[i-1]=='-'){
                if(i==1){
                    a[0]='0';
                    a[i]='-';
                    i++;
                    a[i]=ch;
                }
                if((i!=1)&&(a[i-2]=='(')){
                    a[i-1]='0';
                    a[i]='-';
                    i++;
                    a[i]=ch;
                   }
                else
                    a[i]=ch;
            }
            if((ch>='0'&ch<='9')&a[i-1]=='+'){
                if(i==1){
                    a[--i]=ch;
                }
                else
                    a[i]=ch;
            }
            if((ch=='+'||ch=='-'||ch=='*'||ch=='/')&&(a[i-1]=='+'||a[i-1]=='-'||a[i-1]=='*'||a[i-1]=='/')){
                printf("输入有误！\n");
                x=1;
                break;
            }
            if((ch=='('||ch==')')&&(a[i-1]=='('||a[i-1]==')')){
                printf("输入有误！\n");
                x=1;
                break;
               }
            if((ch=='0')&&a[i-1]=='/'){
                printf("输入有误！\n");
                x=1;
                break;
               }
            else
                a[i]=ch;
            i++;
        }
        ch=getchar();
    }
    a[i]='\0';
    if(x==0&&i>2){
        if((a[i-1]>='0'&&a[i-1]<='9')||a[i-1]==')'){
            qz=(int*)malloc(sizeof(int)*100);
            houzhui(a,nb,qz);
        }
        else{
        fflush(stdin);
        printf("请选择操作1-2:");
        scanf("%d", &cz);
        if(cz==1)
            goto start;
        else
            exit(0);
    }
    }
    else{
        fflush(stdin);
        printf("请选择操作1-2:");
        scanf("%d", &cz);
        if(cz==1)
            goto start;
        else
            exit(0);
    }
    for(i=0;nb[i]!='\0';i++){
        if(nb[i]!='+'&&nb[i]!='-'&&nb[i]!='*'&&nb[i]!='/'){
            push(n,nb[i]);        //数字则直接入栈
        }
        else{
            switch(nb[i]){        //符号，则把处于栈顶的两个数字出栈，进行运算，运算结果入栈
            case '+':
                rs=(pop(n)-'0')+(pop(n)-'0');
                push(n,rs+'0');
                break;
            case '-':
                p=pop(n)-'0';
                rs=(pop(n)-'0')-p;
                push(n,rs+'0');
                break;
            case '*':
                rs=(pop(n)-'0')*(pop(n)-'0');
                push(n,rs+'0');
                break;
            case '/':
                p=pop(n)-'0';
                rs=(pop(n)-'0')/p;
                push(n,rs+'0');
                break;
            }
        }
    }
    InitBiTree(&T);
    CreateBiTree2(&T,qz);             //修改值一定要传指针的指针
    printf("%s化为前缀后为:%s\n",a,qz);
    printf("%s=%d\n",a,Value(T));
    free(qz);
    printf("请选择操作1-2:");
	scanf("%d", &cz);
	if(cz==1)
        goto start;
    return 0;
}

