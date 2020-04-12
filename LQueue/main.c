#include"LQueue.h"
#include <stdio.h>
#include <stdlib.h>

int hpos;             //һ�ж���Ϊ���ܶ�λ��datatype�����λ��
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
    LQueue *Q=NULL;     //�Ӷ������û�Ҫ�ȳ�ʼ��
    printf("	*********************************************************\n");
	printf("	*			1:��ʼ������\n");
	printf("	*			2:���ٶ���\n");
	printf("	*			3:�������Ƿ�Ϊ��\n");
	printf("	*			4:�鿴��ͷԪ��\n");
	printf("	*			5:ȷ�����г���\n");
	printf("	*			6:��Ӳ���\n");
	printf("	*			7:���Ӳ���\n");
	printf("	*			8:��ն���\n");
	printf("	*			9:������������\n");
	printf("	*			10:��������\n");
	printf("	*********************************************************\n");
start:
    printf("���������1-10:");
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
            printf("1.���³�ʼ������\n");
            printf("2.��������\n");
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
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            fh=IsEmptyLQueue(Q);
            if(fh==1){
                printf("����Ϊ�գ�\n");
            }
            else
                printf("���в��ǿյģ�\n");
            break;
        case 4:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            if(IsEmptyLQueue(Q)){
                printf("����Ϊ�գ�\n");
                break;
            }
            if(datatype[hpos]=='i'){
                int i;
                GetHeadLQueue(Q,&i);
                printf("��ͷԪ��Ϊ:%d\n",i);
            }
            if(datatype[hpos]=='f'){
                double f;
                GetHeadLQueue(Q,&f);
                printf("��ͷԪ��Ϊ:%.4lf\n",f);
            }
            if(datatype[hpos]=='c'){
                char c;
                GetHeadLQueue(Q,&c);
                printf("��ͷԪ��Ϊ:%c\n",c);
            }
            if(datatype[hpos]=='s'){
                char s[30];
                GetHeadLQueue(Q,s);
                printf("��ͷԪ��Ϊ:%s\n",s);
            }
            break;
        case 5:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            num=LengthLQueue(Q);
            printf("������%d��Ԫ��\n",num);
            break;
        case 6:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            printf("���Ԫ�ص�����(i:���� ,f:������ ,c:�ַ��� ,s:�ַ�����)\n");
            fflush(stdin);
            type=getchar();
            while(type!='i'&&type!='f'&&type!='c'&&type!='s'){
                printf("�����������������룡\n");
                fflush(stdin);
                type=getchar();
            }
            datatype[npos]=type;
            npos++;
            if(type=='i'){
                printf("������Ҫ��ӵ�������\n");
                int i;
                while(1){
                scanf("%s",a);
                for(x=0,j=0;x<strlen(a);x++)                  //���������ַ���
                {
                    if(a[x]<=57&&a[x]>=48)                  //0~9��ASCII����48~57
                        j++;
                }
                if(j==strlen(a)){                           //��ֹ��������������
                    i=0;
                    for(c=1;j>0;j--){
                        z=(int)a[j-1]-48;
                        i+=z*c;
                        c*=10;
                    }
                    break;
                }
                else
                    printf("����ȷ����������\n�������룺");
                }
                EnLQueue(Q,&i);
            }
            if(type=='f'){
                printf("������Ҫ��ӵĸ�������\n");
                double f;
                scanf("%lf",&f);
                EnLQueue(Q,&f);
            }
            if(type=='c'){
                printf("������Ҫ��ӵ��ַ���\n");
                char c;
                fflush(stdin);     //ȥ���������Ļس���
                scanf("%c",&c);
                EnLQueue(Q,&c);
            }
            if(type=='s'){
                printf("������Ҫ��ӵ��ַ�����\n");
                char s[30];
                scanf("%s",s);
                EnLQueue(Q,s);
            }
            fflush(stdin);
            break;
        case 7:
            fh=DeLQueue(Q);
            //if(fh==1)
               // hpos++;       //��̫�������Ϊʲô����
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
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}
