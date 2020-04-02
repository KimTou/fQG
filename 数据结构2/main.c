#include"duLinkedList.h"
#include <stdio.h>
#include <stdlib.h>

void visit(ElemType e) {
    printf("该链表一共有%d个节点\n", e);
}

int main() {
	int cz,num,i;
    DuLNode * node=NULL;
    ElemType *e;
	DuLinkedList head ;
	InitList_DuL(&head);
	printf("*************************\n");
	printf("*1:销毁链表\n");
	printf("*2:插入节点(前插)\n");
    printf("*3:插入节点(后插)\n");
	printf("*4:删除节点\n");
	printf("*5:遍历链表\n");
	printf("*6:结束程序\n");
	printf("*************************\n");
start:
    printf("输入操作符1-6:");
	scanf("%d", &cz);
	switch (cz)
	{
	case 1:
		DestroyList_DuL(&head);
		printf("1.重构链表\n");
		printf("2.结束程序\n");
		scanf("%d",&num);
		if(num==1){
            InitList_DuL(&head);
            break;
		}
		if(num==2){
            exit(0);
		}
	case 2:
		printf("你想将此节点插入在第几个节点的前面:\n");
		scanf("%d", &num);
		node=head;
		for(i=0;i<num;i++){
            node=node->next;
            if(node==NULL){
            printf("输入有误！\n");
            break;
            }
		}
		if(node==NULL)
            break;
		DuLNode* node2 = (DuLNode*)malloc(sizeof(DuLNode));
		printf("请输入节点数据：\n");
		scanf("%d", &node2->data);
		InsertBeforeList_DuL(node, node2);
		break;

		case 3:
		printf("你想将此节点插入在第几个节点的后面:\n");
		scanf("%d", &num);
		node=head;
		for(i=0;i<num;i++){
            node=node->next;
            if(node==NULL){
            printf("输入有误！\n");
            break;
            }
		}
		if(node==NULL)
            break;
		DuLNode* node3 = (DuLNode*)malloc(sizeof(DuLNode));
		printf("请输入节点数据：\n");
		scanf("%d", &node3->data);
		InsertAfterList_DuL(node, node3);
		break;

	case 4:
	    printf("请输入你想删除第几个节点：\n");
	    scanf("%d",&num);
		node=head;
		for(i=1;i<num;i++){
            node=node->next;
            if(node->next==NULL){
            printf("输入有误！\n");
            break;
            }
		}
		if(node==NULL)
            break;
		DeleteList_DuL(node,e);     //把那个节点的前一个传进去
        break;

	case 5:
		TraverseList_DuL(head,visit);
		break;

	case 6:
		exit(0);
	default: printf("请输入正确的数字！\n");
	}

	goto start;                         //用goto语句实现循环操作
	return 0;
}

