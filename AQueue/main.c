#include"AQueue.h"
#include <stdio.h>
#include <stdlib.h>

void foo(void *q)
{
    APrint(q);
}

int main()
{
    int cz,fh,num;
    int x,j,c,z;
    char a[20];
    AQueue *Q=NULL;     //�Ӷ������û�Ҫ�ȳ�ʼ��
    printf("	*********************************************************\n");
	printf("	*			1:��ʼ������\n");
	printf("	*			2:���ٶ���\n");
	printf("	*			3:�������Ƿ�����\n");
	printf("	*			4:�������Ƿ�Ϊ��\n");
	printf("	*			5:�鿴��ͷԪ��\n");
	printf("	*			6:ȷ�����г���\n");
	printf("	*			7:��Ӳ���\n");
	printf("	*			8:���Ӳ���\n");
	printf("	*			9:��ն���\n");
	printf("	*			10:������������\n");
	printf("	*			11:��������\n");
	printf("	*********************************************************\n");
start:
    printf("���������1-11:");
	scanf("%d", &cz);
	switch (cz)
	{
	    case 1:
	        Q=(AQueue*)malloc(sizeof(AQueue));
            InitAQueue(Q);
            break;
        case 2:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            DestoryAQueue(Q);
            printf("1.���³�ʼ������\n");
            printf("2.��������\n");
            scanf("%d",&fh);
            if(fh==1){
                InitAQueue(Q);
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
            fh=IsFullAQueue(Q);
            if(fh==1)
                printf("����������\n");
            else
                printf("����δ����\n");
            break;
        case 4:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            fh=IsEmptyAQueue(Q);
            if(fh==1){
                printf("����Ϊ�գ�\n");
            }
            else
                printf("���в��ǿյģ�\n");
            break;
        case 5:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            if(IsEmptyAQueue(Q)){
                printf("����Ϊ�գ�\n");
                break;
            }
            if(datatype[Q->front]=='i'){
                int i;
                GetHeadAQueue(Q,&i);
                printf("��ͷԪ��Ϊ:%d\n",i);
            }
            if(datatype[Q->front]=='f'){
                double f;
                GetHeadAQueue(Q,&f);
                printf("��ͷԪ��Ϊ:%.4lf\n",f);
            }
            if(datatype[Q->front]=='c'){
                char c;
                GetHeadAQueue(Q,&c);
                printf("��ͷԪ��Ϊ:%c\n",c);
            }
            if(datatype[Q->front]=='s'){
                char s[30];
                GetHeadAQueue(Q,s);
                printf("��ͷԪ��Ϊ:%s\n",s);
            }
            break;
        case 6:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            num=LengthAQueue(Q);
            printf("������%d��Ԫ��\n",num);
            break;
        case 7:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            printf("���Ԫ�ص�����(i:���� ,f:������ ,c:�ַ��� ,s:�ַ�����)\n");
            fflush(stdin);     //ȥ���������Ļس���
            type=getchar();
            while(type!='i'&&type!='f'&&type!='c'&&type!='s'){
                printf("�����������������룡\n");
                fflush(stdin);
                type=getchar();
            }
            datatype[Q->rear]=type;         //��¼��������
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
                EnAQueue(Q,&i);
            }
            if(type=='f'){
                printf("������Ҫ��ӵĸ�������\n");
                double f;
                scanf("%lf",&f);
                EnAQueue(Q,&f);
            }
            if(type=='c'){
                printf("������Ҫ��ӵ��ַ���\n");
                char c;
                fflush(stdin);     //ȥ���������Ļس���
                scanf("%c",&c);
                EnAQueue(Q,&c);
            }
            if(type=='s'){
                printf("������Ҫ��ӵ��ַ�����\n");
                char s[30];
                scanf("%s",s);
                EnAQueue(Q,s);
            }
            fflush(stdin);
            break;
        case 8:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            DeAQueue(Q);
            break;
        case 9:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            ClearAQueue(Q);
            break;
        case 10:
            if(Q==NULL){
                printf("���ȳ�ʼ�����У�\n");
                break;
            }
            TraverseAQueue(Q,foo);
            break;
        case 11:
            exit(0);
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}
