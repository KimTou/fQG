#include"linkList.h"
#include<stdio.h>
#include<stdlib.h>

/**
 *  @name        : Status InitList(LinkList *L);
 *	@description : initialize an empty linked list with only the head node without value
 *	@param		 : L(the head node)
 *	@return		 : Status
 *  @notice      : None
 */
Status InitList(LinkedList * L) {           //传进来二级指针,因为函数参数是值调用
    int n=0,i,j,c,z;                              //L应当为指针的指针，这样才能改变传进来的指针的指向（地址）
    char a[20];
    LNode* p1, * p2;
    *L = (LinkedList)malloc(sizeof(LNode));
    p1 = p2 = (LNode*)malloc(sizeof(LNode));
    printf("开始构建链表(请输入整数,并以0作为结束)\n");
    while(1){
    scanf("%s",a);
    for(i=0,j=0;i<strlen(a);i++)                  //遍历整个字符串
    {
        if(a[i]<=57&&a[i]>=48)                  //0~9的ASCII码是48~57
            j++;
    }
    if(j==strlen(a)){                           //防止传进来不是整数
        p1->data=0;
        for(c=1;j>0;j--){
            z=(int)a[j-1]-48;
            p1->data+=z*c;
            c*=10;
        }
        break;
    }
    else
        printf("请正确输入整数！\n");
    }
    while (p1->data != 0) {
        n++;
        if (n == 1)
            (*L)->next = p1;
        else
            p2->next = p1;
        p2 = p1;
        p1 = (LNode*)malloc(sizeof(LNode));
        while(1){
            scanf("%s",a);
            for(i=0,j=0;i<strlen(a);i++)                  //遍历整个字符串
            {
                if(a[i]<=57&&a[i]>=48)                  //0~9的ASCII码是48~57
                    j++;
            }
            if(j==strlen(a)){
                p1->data=0;
                for(c=1;j>0;j--){
                    z=(int)a[j-1]-48;
                    p1->data+=z*c;
                    c*=10;
                }
                break;
            }
            else
                printf("请正确输入整数！\n");
        }
    }
    p2->next = NULL;
    return SUCCESS;
}

/**
 *  @name        : void DestroyList(LinkedList *L)
 *	@description : destroy a linked list, free all the nodes
 *	@param		 : L(the head node)
 *	@return		 : None
 *  @notice      : None
 */
void DestroyList(LinkedList* L) {
    LNode* p1 = *L;         //头指针也应当销毁掉
    LNode* p2;
    while (p1 != NULL)
    {
        p2 = p1;
        p1 = p1->next;
        free(p2);
    }
    printf("已销毁链表\n");
}

/**
 *  @name        : Status InsertList(LNode *p, LNode *q)
 *	@description : insert node q after node p
 *	@param		 : p, q
 *	@return		 : Status
 *  @notice      : None
 */
Status InsertList(LNode* p, LNode* q) {
    q->next = p->next;
    p->next = q;
    printf("插入成功！\n");
    return SUCCESS;
}
/**
 *  @name        : Status DeleteList(LNode *p, ElemType *e)
 *	@description : delete the first node after the node p and assign its value to e
 *	@param		 : p, e
 *	@return		 : Status
 *  @notice      : None
 */
Status DeleteList(LNode* p, ElemType *e) {
    LNode* q = p->next;
    p->next = q->next;
    e = q->data;
    printf("删除成功！\n");
    printf("删除的节点的值为：%d\n",e);
    free(q);
    return SUCCESS;
}

/**
 *  @name        : void TraverseList(LinkedList L, void (*visit)(ElemType e))
 *	@description : traverse the linked list and call the funtion visit
 *	@param		 : L(the head node), visit
 *	@return		 : None
 *  @notice      : None
 */
void TraverseList(LinkedList L, void (*visit)(ElemType e)) {
    int i = 0;
    LinkedList p =NULL;
    p=L->next;
    while (p != NULL) {
        if(p->next==NULL)
            printf("%d\n", p->data);
        else
            printf("%d->", p->data);
        p = p->next;
        i++;
    }
    visit(i);
}

/**
 *  @name        : Status SearchList(LinkedList L, ElemType e)
 *	@description : find the first node in the linked list according to e
 *	@param		 : L(the head node), e
 *	@return		 : Status
 *  @notice      : None
 */
Status SearchList(LinkedList L, ElemType e) {
    int i = 1;
    LinkedList p = L->next;
    while (p->data != e && p != NULL) {
        p = p->next;
        i++;
    }
    if (p != NULL) {
        printf("链表的第%d个节点有该值\n", i);
        return SUCCESS;
    }
    if (p == NULL) {
        printf("链表里没有该值\n");
        return ERROR;
    }
}

/**
 *  @name        : Status ReverseList(LinkedList *L)
 *	@description : reverse the linked list
 *	@param		 : L(the head node)
 *	@return		 : Status
 *  @notice      : None
 */
Status ReverseList(LinkedList* L) {
    LinkedList p = (*L)->next;
    LinkedList pr, q = NULL;
    (*L)->next = NULL;
    while (p != NULL) {
        pr = p->next;
        p->next = q;
        q = p;
        p = pr;
    }
    (*L)->next = q;
    printf("反转完成\n");
    return SUCCESS;
}

/**
 *  @name        : Status IsLoopList(LinkedList L)
 *	@description : judge whether the linked list is looped
 *	@param		 : L(the head node)
 *	@return		 : Status
 *  @notice      : None
 */
Status IsLoopList(LinkedList L) {
    LinkedList p1 = NULL;
    LinkedList p2 = NULL;
    p1=p2=L;
    while(p1!=NULL){
        p1=p1->next;
        if(p1==NULL){
            printf("该链表不成环！\n");
            return SUCCESS;
        }
        p1=p1->next;
        p2=p2->next;
        if(p1==p2){
            printf("该链表成环！\n");
            return SUCCESS;
        }
    }
}

/**
 *  @name        : LNode* ReverseEvenList(LinkedList *L)
 *	@description : reverse the nodes which value is an even number in the linked list, input: 1 -> 2 -> 3 -> 4  output: 2 -> 1 -> 4 -> 3
 *	@param		 : L(the head node)
 *	@return		 : LNode(the new head node)
 *  @notice      : choose to finish
 */
LNode* ReverseEvenList(LinkedList* L) {
    LinkedList cur = (*L)->next;
    LinkedList pre = (LNode*)malloc(sizeof(LNode));
    pre->next = cur;
    LinkedList head = pre;                         //若节点个数为奇数，则最后一个不变
    while (cur != NULL && cur->next != NULL) {
        pre->next = cur->next;
        cur->next = pre->next->next;
        pre->next->next = cur;
        pre = cur;
        cur = cur->next;
    }
    printf("对调完成\n");
    return head;
}

/**
 *  @name        : LNode* FindMidNode(LinkedList *L)
 *	@description : find the middle node in the linked list
 *	@param		 : L(the head node)
 *	@return		 : LNode
 *  @notice      : choose to finish
 */
LNode* FindMidNode(LinkedList* L) {
    LinkedList fast = *L;
    LinkedList slow = *L;
    while (fast != NULL) {
        fast = fast->next;            //若节点个数为偶数，则返回相对后面的
        if(fast==NULL)
            break;
        fast=fast->next;
        slow=slow->next;
    }
    return slow;
}

