#include <stdio.h>
#include <stdlib.h>
#include"BinaryTree.h"
#define LEN 200
typedef struct SqStack
{
	char *fh;
	int top;
	int size;
} SqStack;

void visit(TElemType e)
{
    printf("%c ",e);
}

int main()
{
    int cz;
    int zt;
    char *definition=NULL;
    struct BiTNode *T;
    printf("	*********************************************************\n");
    printf("	*			0:使用计算器\n");
	printf("	*			1:构造二叉树\n");
	printf("	*			2:销毁二叉树\n");
	printf("	*			3:先序遍历（递归）\n");
	printf("	*			4:中序遍历（递归）\n");
	printf("	*			5:后序遍历（递归）\n");
	printf("	*			6:先序遍历（非递归）\n");
	printf("	*			7:中序遍历（非递归）\n");
	printf("	*			8:后序遍历（非递归）\n");
	printf("	*			9:结束程序\n");
	printf("	*********************************************************\n");
    start:                  //goto 循环
    printf("\n输入操作符0-9:");
    fflush(stdin);
	scanf("%d", &cz);
	switch (cz)
	{
        case 0:
            InitBiTree(&T);
	        printf("请输入前缀表达式\n");
	        definition=(char*)malloc(sizeof(char)*200);
	        fflush(stdin);      //把缓冲区的'\n'去掉
	        char ch;
            ch=getchar();
            int i;
            for(i=0;ch!='\n';){
                if((ch>='0'&&ch<='9')||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='('||ch==')'){
                    definition[i]=ch;
                    i++;
                    ch=getchar();
                }
                else
                    ch=getchar();
            }
            definition[i]='\0';
            CreateBiTree2(&T,definition);             //修改值一定要传指针的指针
            printf("%s=%d\n",definition,Value(T));
            free(definition);
            break;
	    case 1:
	        InitBiTree(&T);
	        printf("请输入二叉树的定义（用#代表空子树）\n");
	        definition=(char*)malloc(sizeof(char)*200);
	        scanf("%s",definition);
            zt=CreateBiTree(&T,definition);             //修改值一定要传指针的指针
            if(zt==1){
                printf("构造成功！\n");
            }
            else
                printf("构造失败！\n");
            break;
        case 2:
            if(NULL==T){
                printf("请先二叉树！");
                break;
            }
            DestroyBiTree(T);
            printf("销毁成功！\n");
            free(definition);               //释放定义的内存
            int xz=1;
            while(xz==1||xz==2){
                printf("1.重建二叉树\n");
                printf("2.结束程序\n");
                scanf("%d",&xz);
                fflush(stdin);
                if(xz==1){
                    InitBiTree(&T);
                    printf("请输入二叉树的定义（用#代表空子树）\n");
                    definition=(char*)malloc(sizeof(char)*200);
                    scanf("%s",definition);
                    fflush(stdin);
                    zt=CreateBiTree(&T,definition);
                    if(zt==1){
                        printf("构造成功！\n");
                    }
                    else
                        printf("构造失败！\n");
                    break;
                }
                if(xz==2){
                    exit(0);
                }
                else{
                    printf("输入有误，请重新输入！\n");
                    break;
                }
            }
        case 3:
            PreOrderTraverse(T,visit);
            break;
        case 4:
            InOrderTraverse(T,visit);
            break;
        case 5:
            PostOrderTraverse(T,visit);
            break;
        case 6:
            PreOrder(T,visit);
            break;
        case 7:
            InOrder(T,visit);
            break;
        case 8:
            PostOrder(T,visit);
            break;
        case 9:
            exit(0);
	default: printf("请输入正确的数字！\n");
	}

	goto start;                         //用goto语句实现循环操作
	return 0;
}
