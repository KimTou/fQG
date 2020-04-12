#include"LQueue.h"
#include <stdio.h>
#include <stdlib.h>

int hpos;             //一切都是为了能定位到datatype数组的位置
int npos;
void foo(void *q)
{
    LPrint(q);
}

int main()
{
    int cz,fh,num;
    int x,j,c,z;
    char a[20];
    LQueue *Q=NULL;     //从而提醒用户要先初始化
    printf("	*********************************************************\n");
	printf("	*			1:初始化队列\n");
	printf("	*			2:销毁队列\n");
	printf("	*			3:检查队列是否为空\n");
	printf("	*			4:查看队头元素\n");
	printf("	*			5:确定队列长度\n");
	printf("	*			6:入队操作\n");
	printf("	*			7:出队操作\n");
	printf("	*			8:清空队列\n");
	printf("	*			9:遍历函数操作\n");
	printf("	*			10:结束程序\n");
	printf("	*********************************************************\n");
start:
    printf("输入操作符1-10:");
	scanf("%d", &cz);
	switch (cz)
	{
	    case 1:
	        Q=(LQueue*)malloc(sizeof(LQueue));
            InitLQueue(Q);
            hpos=0;
            npos=0;
            break;
        case 2:
            DestoryLQueue(Q);
            hpos=npos;
            printf("1.重新初始化队列\n");
            printf("2.结束程序\n");
            scanf("%d",&fh);
            if(fh==1){
                InitLQueue(Q);
                break;
            }
            if(fh==2){
                exit(0);
            }
            else
                break;
        case 3:
            if(Q==NULL){
                printf("请先初始化队列！\n");
                break;
            }
            fh=IsEmptyLQueue(Q);
            if(fh==1){
                printf("队列为空！\n");
            }
            else
                printf("队列不是空的！\n");
            break;
        case 4:
            if(Q==NULL){
                printf("请先初始化队列！\n");
                break;
            }
            if(IsEmptyLQueue(Q)){
                printf("队列为空！\n");
                break;
            }
            if(datatype[hpos]=='i'){
                int i;
                GetHeadLQueue(Q,&i);
                printf("队头元素为:%d\n",i);
            }
            if(datatype[hpos]=='f'){
                double f;
                GetHeadLQueue(Q,&f);
                printf("队头元素为:%.4lf\n",f);
            }
            if(datatype[hpos]=='c'){
                char c;
                GetHeadLQueue(Q,&c);
                printf("队头元素为:%c\n",c);
            }
            if(datatype[hpos]=='s'){
                char s[30];
                GetHeadLQueue(Q,s);
                printf("队头元素为:%s\n",s);
            }
            break;
        case 5:
            if(Q==NULL){
                printf("请先初始化队列！\n");
                break;
            }
            num=LengthLQueue(Q);
            printf("队列有%d个元素\n",num);
            break;
        case 6:
            if(Q==NULL){
                printf("请先初始化队列！\n");
                break;
            }
            printf("入队元素的类型(i:整型 ,f:浮点型 ,c:字符型 ,s:字符串型)\n");
            fflush(stdin);
            type=getchar();
            while(type!='i'&&type!='f'&&type!='c'&&type!='s'){
                printf("输入有误，请重新输入！\n");
                fflush(stdin);
                type=getchar();
            }
            datatype[npos]=type;
            npos++;
            if(type=='i'){
                printf("请输入要入队的整数：\n");
                int i;
                while(1){
                scanf("%s",a);
                for(x=0,j=0;x<strlen(a);x++)                  //遍历整个字符串
                {
                    if(a[x]<=57&&a[x]>=48)                  //0~9的ASCII码是48~57
                        j++;
                }
                if(j==strlen(a)){                           //防止传进来不是整数
                    i=0;
                    for(c=1;j>0;j--){
                        z=(int)a[j-1]-48;
                        i+=z*c;
                        c*=10;
                    }
                    break;
                }
                else
                    printf("请正确输入整数！\n重新输入：");
                }
                EnLQueue(Q,&i);
            }
            if(type=='f'){
                printf("请输入要入队的浮点数：\n");
                double f;
                scanf("%lf",&f);
                EnLQueue(Q,&f);
            }
            if(type=='c'){
                printf("请输入要入队的字符：\n");
                char c;
                fflush(stdin);     //去掉缓冲区的回车键
                scanf("%c",&c);
                EnLQueue(Q,&c);
            }
            if(type=='s'){
                printf("请输入要入队的字符串：\n");
                char s[30];
                scanf("%s",s);
                EnLQueue(Q,s);
            }
            fflush(stdin);
            break;
        case 7:
            fh=DeLQueue(Q);
            //if(fh==1)
               // hpos++;       //不太清楚这里为什么不用
            break;
        case 8:
            ClearLQueue(Q);
            break;
        case 9:
            TraverseLQueue(Q,foo);
            printf("\n");
            break;
        case 10:
            exit(0);
	default: printf("请输入正确的数字！\n");
	}

	goto start;                         //用goto语句实现循环操作
	return 0;
}
