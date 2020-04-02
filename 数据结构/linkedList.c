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
Status InitList(LinkedList * L) {           //����������ָ��,��Ϊ����������ֵ����
    int n=0,i,j,c,z;                              //LӦ��Ϊָ���ָ�룬�������ܸı䴫������ָ���ָ�򣨵�ַ��
    char a[20];
    LNode* p1, * p2;
    *L = (LinkedList)malloc(sizeof(LNode));
    p1 = p2 = (LNode*)malloc(sizeof(LNode));
    printf("��ʼ��������(����������,����0��Ϊ����)\n");
    while(1){
    scanf("%s",a);
    for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
    {
        if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
            j++;
    }
    if(j==strlen(a)){                           //��ֹ��������������
        p1->data=0;
        for(c=1;j>0;j--){
            z=(int)a[j-1]-48;
            p1->data+=z*c;
            c*=10;
        }
        break;
    }
    else
        printf("����ȷ����������\n");
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
            for(i=0,j=0;i<strlen(a);i++)                  //���������ַ���
            {
                if(a[i]<=57&&a[i]>=48)                  //0~9��ASCII����48~57
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
                printf("����ȷ����������\n");
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
    LNode* p1 = *L;         //ͷָ��ҲӦ�����ٵ�
    LNode* p2;
    while (p1 != NULL)
    {
        p2 = p1;
        p1 = p1->next;
        free(p2);
    }
    printf("����������\n");
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
    printf("����ɹ���\n");
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
    printf("ɾ���ɹ���\n");
    printf("ɾ���Ľڵ��ֵΪ��%d\n",e);
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
        printf("����ĵ�%d���ڵ��и�ֵ\n", i);
        return SUCCESS;
    }
    if (p == NULL) {
        printf("������û�и�ֵ\n");
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
    printf("��ת���\n");
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
            printf("�������ɻ���\n");
            return SUCCESS;
        }
        p1=p1->next;
        p2=p2->next;
        if(p1==p2){
            printf("������ɻ���\n");
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
    LinkedList head = pre;                         //���ڵ����Ϊ�����������һ������
    while (cur != NULL && cur->next != NULL) {
        pre->next = cur->next;
        cur->next = pre->next->next;
        pre->next->next = cur;
        pre = cur;
        cur = cur->next;
    }
    printf("�Ե����\n");
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
        fast = fast->next;            //���ڵ����Ϊż�����򷵻���Ժ����
        if(fast==NULL)
            break;
        fast=fast->next;
        slow=slow->next;
    }
    return slow;
}

