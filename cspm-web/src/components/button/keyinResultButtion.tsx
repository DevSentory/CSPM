'use client'
import React, { useState } from 'react'
import ResultKeyInModal from '@/components/modal/resultKeyInModal'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useCheckedItemsStore } from '@/components/Zustand/accountPageStore'

// KeyinResultButton 컴포넌트에서 props로 checkedItems를 받아오고, 클릭 가능한 상태를 조건부로 설정합니다.
export default function KeyInResultButton() {
  const [showModal, setShowModal] = useState(false)
  const { checkedItems, setCheckedItems } = useCheckedItemsStore()
  const index = checkedItems.values().next().value

  const handleShowModal = () => {
    if (checkedItems && checkedItems.size > 0 && checkedItems.size < 2) {
      setShowModal(true)
    } else {
      alert('한개의 체크박스를 선택해주세요.')
    }
  }
  return (
    <div>
      <ButtonLayer buttonStyle='bg-kyboNavy' method={handleShowModal} childText='결과 입력' />
      <ResultKeyInModal showModal={showModal} setShowModal={setShowModal} index={index} />
    </div>
  )
}
