#include"LinkStack.h"
#include <stdio.h>
#include <stdlib.h>

int main()
{
    LinkStack *s=NULL;      //�Ӷ������û�Ҫ�ȳ�ʼ��
    int cz,len,fh;
    int i,j,c,z;       //��ֹ���벻������
    char a[20];
    ElemType p,*q;
    printf("	*********************************************************\n");
	printf("	*			1:��ʼ��ջ\n");
	printf("	*			2:�ж�ջ�Ƿ�Ϊ��\n");
	printf("	*			3:�õ�ջ��Ԫ��\n");
	printf("	*			4:���ջ\n");
	printf("	*			5:����ջ\n");
	printf("	*			6:���ջ����\n");
	printf("	*			7:��ջ\n");
	printf("	*			8:��ջ\n");
	printf("	*			9:��������\n");
	printf("	*********************************************************\n");
    start:                  //goto ѭ��
    printf("���������1-9:");
	scanf("%d", &cz);
	switch (cz)
	{
	    case 1:
	        s=(LinkStack*)malloc(sizeof(LinkStack));   //�Ⱥ�ǰ������LinkStack *s
            initLStack(s);
            break;
        case 2:
            isEmptyLStack(s);
            break;
        case 3:
            fh=getTopLStack(s,&q);
            if(fh==1)
                printf("ջ��Ԫ��Ϊ:%d\n",*q);
            break;
        case 4:
            clearLStack(s);
            break;
        case 5:
            fh=destroyLStack(s);
            if(fh!=0){
                printf("1.�ؽ�ջ\n");
                printf("2.��������\n");
                scanf("%d",&len);
                if(len==1){
                    initLStack(s);
                    break;
                }
                if(len==2){
                    exit(0);
                }
                else
                    break;
            }
            else
                break;
        case 6:
            LStackLength(s,q);              //��case 8��ͬ�����ﲻ�ô���ȥָ���ָ�룡����
            printf("ջ��%d��Ԫ��\n",*q);
            break;
        case 7:
            printf("������Ҫ��ջ��ֵ(����)��");
            while(1){
                scanf("%s",a);
                for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
                {
                    if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
                        j++;
                }
                if(j==strlen(a)){                           //��ֹ��������������
                    p=0;
                    for(c=1;j>0;j--){
                        z=(int)a[j-1]-48;
                        p+=z*c;
                        c*=10;
                    }
                    break;
                }
                else
                    printf("����ȷ����������\n�������룺");
                }
            pushLStack(s,p);
            break;
        case 8:
            fh=popLStack(s,&q);      //Ӧ����ȥָ���ָ�룡����
            if(fh==1)
                printf("��ջ��ֵΪ��%d\n",q);
            break;
        case 9:
            exit(0);
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}
