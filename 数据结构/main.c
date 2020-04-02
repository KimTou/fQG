#include"linkList.h"
#include<stdio.h>
#include<stdlib.h>

void visit(ElemType e) {
    printf("该链表一共有%d个节点\n", e);
}

int main() {
	int cz,num,i;
    LNode * node=NULL;
    ElemType *e;
	LinkedList head ;
	InitList(&head);
	printf("	*********************************************************\n");
	printf("	*			1:销毁链表\n");
	printf("	*			2:插入节点\n");
	printf("	*			3:删除节点\n");
	printf("	*			4:遍历链表\n");
	printf("	*			5:寻找节点\n");
	printf("	*			6:反转链表\n");
	printf("	*			7:判断成环\n");
	printf("	*			8:奇偶互换\n");
	printf("	*			9:寻找中点\n");
	printf("	*			10:结束程序\n");
	printf("	*********************************************************\n");
start:
    printf("输入操作符1-10:");
	scanf("%d", &cz);
	switch (cz)
	{
	case 1:
		DestroyList(&head);
		printf("1.重构链表\n");
		printf("2.结束程序\n");
		scanf("%d",&num);
		if(num==1){
            InitList(&head);
            break;
		}
		if(num==2){
            exit(0);
		}
	case 2:
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
		LNode* node2 = (LNode*)malloc(sizeof(LNode));
		printf("请输入节点数据：\n");
		scanf("%d", &node2->data);
		InsertList(node, node2);
		break;
	case 3:
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
		DeleteList(node,e);
        break;

	case 4:
		TraverseList(head,visit);
		break;
	case 5:
	    printf("你想寻找的节点的值为：");
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
	    //LNode * node=NULL;               不能在这里定义，标签后边并非不能声明变量，只是不能紧跟着标签声明变量
		node = FindMidNode(&head);        //指针要被赋值则需要有指向(如:NULL),若要开辟新空间，则不能有指向
		printf("中点的值为%d\n", node->data);
		break;
	case 10:
		exit(0);
	default: printf("请输入正确的数字！\n");
	}

	goto start;                         //用goto语句实现循环操作
	return 0;
}
