'use client'

import { useCheckedItemsStore } from '@/components/Zustand/accountPageStore'
import AccountDeleteModal from '@/components/modal/accountDeleteModal'
import { useState } from 'react'

export default function AccountCheckDeleteButton() {
  const [showModal, setShowModal] = useState(false)
  const { checkedItems } = useCheckedItemsStore()
  const handleShowModal = () => {
    {
      checkedItems.size > 0 ? setShowModal(true) : alert('테이블에서 계정을 선택해 주세요')
    }
  }
  return (
    <div>
      <button
        className='h-9 w-24 rounded bg-kyboNavy text-white'
        onClick={() => {
          handleShowModal()
        }}
      >
        대상 삭제
      </button>
      <AccountDeleteModal showModal={showModal} setShowModal={setShowModal} />
    </div>
  )
}
