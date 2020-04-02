#include"linkList.h"
#include<stdio.h>
#include<stdlib.h>

void visit(ElemType e) {
    printf("������һ����%d���ڵ�\n", e);
}

int main() {
	int cz,num,i;
    LNode * node=NULL;
    ElemType *e;
	LinkedList head ;
	InitList(&head);
	printf("	*********************************************************\n");
	printf("	*			1:��������\n");
	printf("	*			2:����ڵ�\n");
	printf("	*			3:ɾ���ڵ�\n");
	printf("	*			4:��������\n");
	printf("	*			5:Ѱ�ҽڵ�\n");
	printf("	*			6:��ת����\n");
	printf("	*			7:�жϳɻ�\n");
	printf("	*			8:��ż����\n");
	printf("	*			9:Ѱ���е�\n");
	printf("	*			10:��������\n");
	printf("	*********************************************************\n");
start:
    printf("���������1-10:");
	scanf("%d", &cz);
	switch (cz)
	{
	case 1:
		DestroyList(&head);
		printf("1.�ع�����\n");
		printf("2.��������\n");
		scanf("%d",&num);
		if(num==1){
            InitList(&head);
            break;
		}
		if(num==2){
            exit(0);
		}
	case 2:
		printf("���뽫�˽ڵ�����ڵڼ����ڵ�ĺ���:\n");
		scanf("%d", &num);
		node=head;
		for(i=0;i<num;i++){
            node=node->next;
            if(node==NULL){
            printf("��������\n");
            break;
            }
		}
		if(node==NULL)
            break;
		LNode* node2 = (LNode*)malloc(sizeof(LNode));
		printf("������ڵ����ݣ�\n");
		scanf("%d", &node2->data);
		InsertList(node, node2);
		break;
	case 3:
	    printf("����������ɾ���ڼ����ڵ㣺\n");
	    scanf("%d",&num);
		node=head;
		for(i=1;i<num;i++){
            node=node->next;
            if(node->next==NULL){
            printf("��������\n");
            break;
            }
		}
		if(node==NULL)
            break;
		DeleteList(node,e);
        break;

	case 4:
		TraverseList(head,visit);
		break;
	case 5:
	    printf("����Ѱ�ҵĽڵ��ֵΪ��");
        scanf("%d",&num);
		SearchList(head,num);
        break;
	case 6:
		ReverseList(&head);
		break;
	case 7:
		IsLoopList(head);
		break;
	case 8:
		head = ReverseEvenList(&head);
		break;
	case 9:
	    //LNode * node=NULL;               ���������ﶨ�壬��ǩ��߲��ǲ�������������ֻ�ǲ��ܽ����ű�ǩ��������
		node = FindMidNode(&head);        //ָ��Ҫ����ֵ����Ҫ��ָ��(��:NULL),��Ҫ�����¿ռ䣬������ָ��
		printf("�е��ֵΪ%d\n", node->data);
		break;
	case 10:
		exit(0);
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}
