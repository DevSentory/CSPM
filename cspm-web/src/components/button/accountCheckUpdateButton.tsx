'use client'

import { useCheckedItemsStore, useInputStore } from '@/components/Zustand/accountPageStore'
import { useSelectType } from '@/components/Zustand/store'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useRouter } from 'next/navigation'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
interface Props {
  accessKey: string
  secretKey: string
  client: string
  code: string
  comment: string
  accountName: string
  region: string
}

export default function TargetUpdateButton() {
  const router = useRouter()
  const { checkedItems } = useCheckedItemsStore()
  const {
    inputAccountId,
    inputAccountName,
    setInputClientValue,
    setInputCode,
    setInputAccessKey,
    setInputAccessSecretKey,
    setInputAccountName,
    setInputComment,
    setBeforeAccountName,
  } = useInputStore()
  const { setRegionSelected } = useSelectType()

  const getAccountFetch = async () => {
    try {
      const response = await fetch(`${BASE_URL}/accounts/${inputAccountId}/${inputAccountName}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
        credentials: 'include',
      })

      if (response.ok) {
        const inner = await response.json()
        const data: Props = inner.result

        setInputAccessKey(data.accessKey)
        setInputAccessSecretKey(data.secretKey)
        setInputClientValue(data.client)
        setInputCode(data.code)
        setInputComment(data.comment)
        setInputAccountName(data.accountName)
        setRegionSelected(data.region)
        setBeforeAccountName(data.accountName)
      }
    } catch (error) {}
  }

  const handleUpdate = () => {
    if (checkedItems.size === 1) {
      getAccountFetch()
      router.push(`/config/account/accountedit`)
    } else {
      alert('테이블에서 하나만 선택해야합니다.')
    }
  }
  return <ButtonLayer buttonStyle='bg-kyboNavy' method={handleUpdate} childText='대상 수정' />
}
