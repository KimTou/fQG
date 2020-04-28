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
    printf("	*			0:ʹ�ü�����\n");
	printf("	*			1:���������\n");
	printf("	*			2:���ٶ�����\n");
	printf("	*			3:����������ݹ飩\n");
	printf("	*			4:����������ݹ飩\n");
	printf("	*			5:����������ݹ飩\n");
	printf("	*			6:����������ǵݹ飩\n");
	printf("	*			7:����������ǵݹ飩\n");
	printf("	*			8:����������ǵݹ飩\n");
	printf("	*			9:��������\n");
	printf("	*********************************************************\n");
    start:                  //goto ѭ��
    printf("\n���������0-9:");
    fflush(stdin);
	scanf("%d", &cz);
	switch (cz)
	{
        case 0:
            InitBiTree(&T);
	        printf("������ǰ׺���ʽ\n");
	        definition=(char*)malloc(sizeof(char)*200);
	        fflush(stdin);      //�ѻ�������'\n'ȥ��
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
            CreateBiTree2(&T,definition);             //�޸�ֵһ��Ҫ��ָ���ָ��
            printf("%s=%d\n",definition,Value(T));
            free(definition);
            break;
	    case 1:
	        InitBiTree(&T);
	        printf("������������Ķ��壨��#�����������\n");
	        definition=(char*)malloc(sizeof(char)*200);
	        scanf("%s",definition);
            zt=CreateBiTree(&T,definition);             //�޸�ֵһ��Ҫ��ָ���ָ��
            if(zt==1){
                printf("����ɹ���\n");
            }
            else
                printf("����ʧ�ܣ�\n");
            break;
        case 2:
            if(NULL==T){
                printf("���ȶ�������");
                break;
            }
            DestroyBiTree(T);
            printf("���ٳɹ���\n");
            free(definition);               //�ͷŶ�����ڴ�
            int xz=1;
            while(xz==1||xz==2){
                printf("1.�ؽ�������\n");
                printf("2.��������\n");
                scanf("%d",&xz);
                fflush(stdin);
                if(xz==1){
                    InitBiTree(&T);
                    printf("������������Ķ��壨��#�����������\n");
                    definition=(char*)malloc(sizeof(char)*200);
                    scanf("%s",definition);
                    fflush(stdin);
                    zt=CreateBiTree(&T,definition);
                    if(zt==1){
                        printf("����ɹ���\n");
                    }
                    else
                        printf("����ʧ�ܣ�\n");
                    break;
                }
                if(xz==2){
                    exit(0);
                }
                else{
                    printf("�����������������룡\n");
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
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}
