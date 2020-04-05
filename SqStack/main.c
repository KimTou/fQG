#include"SqStack.h"
#include<stdio.h>
#include<stdlib.h>

int main()
{
    int cz,sizes,fh;
    int i,j,c,z;       //防止输入不是整数
    char a[20];
    ElemType p,*q;
    SqStack *s=NULL;     //从而提醒用户要先初始化
    printf("	*********************************************************\n");
	printf("	*			1:初始化栈\n");
	printf("	*			2:判断栈是否为空\n");
	printf("	*			3:得到栈顶元素\n");
	printf("	*			4:清空栈\n");
	printf("	*			5:销毁栈\n");
	printf("	*			6:检测栈长度\n");
	printf("	*			7:入栈\n");
	printf("	*			8:出栈\n");
	printf("	*			9:结束程序\n");
	printf("	*********************************************************\n");
start:
    printf("输入操作符1-9:");
	scanf("%d", &cz);
	switch (cz)
	{
	    case 1:
	        s=(SqStack*)malloc(sizeof(SqStack));
	        printf("请先定义栈的最大空间:\n");
	        scanf("%d",&sizes);
            initStack(s,sizes);
            break;
        case 2:
            isEmptyStack(s);
            break;
        case 3:
            fh=getTopStack(s,&q);
            if(fh==1)
                printf("栈顶元素为:%d\n",*q);
            break;
        case 4:
            clearStack(s);
            break;
        case 5:
            destroyStack(s);
            printf("1.重建栈\n");
            printf("2.结束程序\n");
            scanf("%d",&fh);
            if(fh==1){
                printf("请先定义栈的最大空间:\n");
                scanf("%d",&sizes);
                initStack(s,sizes);
                break;
            }
            if(fh==2){
                exit(0);
            }
            else
                break;
        case 6:
            stackLength(s,q);              //与case 8不同，这里不用传进去指针的指针！！！
            printf("栈有%d个元素\n",*q);
            break;
        case 7:
            printf("请输入要入栈的值(整数)：");
            while(1){
                scanf("%s",a);
                for(i=0,j=0;i<strlen(a);i++)                  //遍历整个字符串
                {
                    if(a[i]<=57&&a[i]>=48)                  //0~9的ASCII码是48~57
                        j++;
                }
                if(j==strlen(a)){                           //防止传进来不是整数
                    p=0;
                    for(c=1;j>0;j--){
                        z=(int)a[j-1]-48;
                        p+=z*c;
                        c*=10;
                    }
                    break;
                }
                else
                    printf("请正确输入整数！\n重新输入：");
                }
            pushStack(s,p);
            break;
        case 8:
            fh=popStack(s,&q);      //应传进去指针的指针！！！
            if(fh==1)
                printf("出栈的值为：%d\n",*q);
            break;
        case 9:
            exit(0);
	default: printf("请输入正确的数字！\n");
	}

	goto start;                         //用goto语句实现循环操作
	return 0;
}
