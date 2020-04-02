#include"duLinkedList.h"
#include <stdio.h>
#include <stdlib.h>

void visit(ElemType e) {
    printf("������һ����%d���ڵ�\n", e);
}

int main() {
	int cz,num,i;
    DuLNode * node=NULL;
    ElemType *e;
	DuLinkedList head ;
	InitList_DuL(&head);
	printf("*************************\n");
	printf("*1:��������\n");
	printf("*2:����ڵ�(ǰ��)\n");
    printf("*3:����ڵ�(���)\n");
	printf("*4:ɾ���ڵ�\n");
	printf("*5:��������\n");
	printf("*6:��������\n");
	printf("*************************\n");
start:
    printf("���������1-6:");
	scanf("%d", &cz);
	switch (cz)
	{
	case 1:
		DestroyList_DuL(&head);
		printf("1.�ع�����\n");
		printf("2.��������\n");
		scanf("%d",&num);
		if(num==1){
            InitList_DuL(&head);
            break;
		}
		if(num==2){
            exit(0);
		}
	case 2:
		printf("���뽫�˽ڵ�����ڵڼ����ڵ��ǰ��:\n");
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
		DuLNode* node2 = (DuLNode*)malloc(sizeof(DuLNode));
		printf("������ڵ����ݣ�\n");
		scanf("%d", &node2->data);
		InsertBeforeList_DuL(node, node2);
		break;

		case 3:
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
		DuLNode* node3 = (DuLNode*)malloc(sizeof(DuLNode));
		printf("������ڵ����ݣ�\n");
		scanf("%d", &node3->data);
		InsertAfterList_DuL(node, node3);
		break;

	case 4:
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
		DeleteList_DuL(node,e);     //���Ǹ��ڵ��ǰһ������ȥ
        break;

	case 5:
		TraverseList_DuL(head,visit);
		break;

	case 6:
		exit(0);
	default: printf("��������ȷ�����֣�\n");
	}

	goto start;                         //��goto���ʵ��ѭ������
	return 0;
}

