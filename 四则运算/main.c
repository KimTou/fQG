#include"cal.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#define LEN 200


void initStack(SqStack *s)//��ʼ��ջ
{
    s->fh=(char*)malloc(LEN*sizeof(char));
    s->top=-1;
    s->size=LEN;
}

void push(SqStack *s,char fh)//��ջ
{
    if(s->top<(s->size-1))
    {
        s->fh[++s->top]=fh;
    }
    else
        printf("ջ�ռ䲻�㣡\n");
}
char pop(SqStack *s)//��ջ
{
    return s->fh[s->top--];
}
char TopStack(SqStack *s) //�õ�ջ��Ԫ��
{
    return s->fh[s->top];
}

void houzhui(char *a,char *nb)  //����׺���ʽת��Ϊ��׺���ʽ
{
    SqStack *s=NULL;
    s=(SqStack*)malloc(sizeof(SqStack));          //����Ҫ�������ʼ��
    initStack(s);
    int i,j,v;
    for(i=0,j=0;a[i]!='\0';i++)
    {
        if(a[i]>='0'&&a[i]<='9'){
            v=0;
            while(a[i]>='0'&&a[i]<='9'){
                v*=10;
                v+=(a[i]-'0');             //Ϊ����ȷʶ���λ��
                i++;
            }
            nb[j++]=(v+'0');              //���֣�ֱ�����.
            i--;
        }
        else {
            switch(a[i]){
                case '+':       //ջ�����������ջ��ֱ��ջ��Ԫ��Ϊ�����Ż�գ���󽫵�ǰ�������ջ��
                case '-':
                    while(TopStack(s)!='('&&s->top>=0){
                        nb[j++]=pop(s);
                    }
                    push(s,a[i]);
                    break;
                case '*':        //if (���ȼ�����ջ������� ||ջ��Ϊ������)   ��ջ
                case '/':
                    if(TopStack(s)=='+'||TopStack(s)=='-'||TopStack(s)=='('||s->top==-1){
                        push(s,a[i]);
                        break;
                    }
                    if(TopStack(s)=='*'||TopStack(s)=='/'){
                        while(TopStack(s)!='('&&s->top>=0){
                                nb[j++]=pop(s);
                        }
                        push(s,a[i]);
                        break;
                    }

                case '(':
                    push(s,a[i]);       //�����ţ�ֱ����ջ
                    break;
                case ')':
                    while(TopStack(s)!='('){
                            nb[j++]=pop(s);   //�����ţ���ջ�����������ջ����ջ�ٳ�ջ��ֱ�������š����Ų������
                          }
                          pop(s);
                          break;
                default:
                    break;
            }
        }
    }
    while(s->top!=-1){
        nb[j++]=pop(s);          //ʣ�µ�ȫ����ջ
    }
    nb[j]='\0';
}

int main()
{
    SqStack *n=NULL;
    n=(SqStack*)malloc(sizeof(SqStack));
    initStack(n);
    int i,rs,p,cz;
    char a[50],nb[50],ch;
    printf("	*********************************************************\n");
    printf("	*			1:��������\n");
	printf("	*			2:�˳�������\n");
    printf("	*********************************************************\n");
    printf("���������1-2:");
	scanf("%d", &cz);
	while(cz==1){
    printf("��������׺���ʽ��\n");
    fflush(stdin);      //�ѻ�������'\n'ȥ��
    ch=getchar();
    for(i=0;ch!='\n';){
        if((ch>='0'&&ch<='9')||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'){
            a[i]=ch;                  //�ɱ�֤���������Ч���ֺ��������ȡ
            i++;                      //������ֻ�����ֺ������
        }
        ch=getchar();
    }
    a[i]='\0';
    houzhui(a,nb);
    for(i=0;nb[i]!='\0';i++){
        if(nb[i]!='+'&&nb[i]!='-'&&nb[i]!='*'&&nb[i]!='/'){
            push(n,nb[i]);        //������ֱ����ջ
        }
        else{
            switch(nb[i]){        //���ţ���Ѵ���ջ�����������ֳ�ջ���������㣬��������ջ
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
    printf("%s=%d\n",a,rs);
    printf("��ѡ�����1-2:");
	scanf("%d", &cz);
	}
    return 0;
}

